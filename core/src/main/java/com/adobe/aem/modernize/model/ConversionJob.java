package com.adobe.aem.modernize.model;

/*-
 * #%L
 * AEM Modernize Tools - Core
 * %%
 * Copyright (C) 2019 - 2021 Adobe Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.JobManager;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.modernize.component.job.ComponentJobExecutor;
import com.adobe.aem.modernize.form.job.FormConversionJobExecutor;
import com.adobe.aem.modernize.job.FullConversionJobExecutor;
import com.adobe.aem.modernize.policy.job.PolicyJobExecutor;
import com.adobe.aem.modernize.structure.job.PageStructureJobExecutor;
import com.day.cq.wcm.api.NameConstants;
import lombok.Getter;

@Model(
    adaptables = Resource.class,
    resourceType = ConversionJob.RESOURCE_TYPE
)
@Getter
public class ConversionJob {

  private static final List<Job.JobState> JOB_STATE_PRIORITY = Arrays.asList(
      Job.JobState.ERROR,
      Job.JobState.DROPPED,
      Job.JobState.GIVEN_UP,
      Job.JobState.STOPPED,
      Job.JobState.ACTIVE,
      Job.JobState.QUEUED,
      Job.JobState.SUCCEEDED
  );

  @OSGiService
  private JobManager jobManager;

  public static final String RESOURCE_TYPE = "aem-modernize/components/job";

  public static final String JOB_DATA_LOCATION = "/var/aem-modernize/job-data";

  public static final String PN_TITLE = NameConstants.PN_TITLE;
  public static final String PN_OVERWRITE = "overwrite";
  public static final String PN_TEMPLATE_RULES = "templateRules";
  public static final String PN_COMPONENT_RULES = "componentRules";
  public static final String PN_POLICY_RULES = "policyRules";
  public static final String PN_SOURCE_ROOT = "sourceRoot";
  public static final String PN_TARGET_ROOT = "targetRoot";
  public static final String PN_CONF_PATH = "confPath";
  public static final String PN_INITIATOR = "startedBy";
  public static final String PN_PAGE_HANDLING = "pageHandling";
  public static final String PN_PRE_MODERNIZE_VERSION = "cq:premodernizeVersion";
  public static final String PN_TYPE = "type";
  public static final String PN_FINISHED = "finished";

  @ValueMapValue
  @Named(PN_TITLE)
  private String title;

  @ValueMapValue
  @Named(PN_OVERWRITE)
  @Optional
  private boolean overwrite;

  @ValueMapValue
  @Named(PN_CONF_PATH)
  @Optional
  private String confPath;

  @ValueMapValue
  @Named(PN_FINISHED)
  @Optional
  private Calendar finished;

  @Inject
  private List<ConversionJobBucket> buckets;


  private Status status;

  public Type getType() {
    return Type.FULL;
  }

  public Status getStatus() {
    if (status == null) {
      Job priorityJob = getPriorityJob();
      status = Status.SUCCESS;
      if (priorityJob != null) {
        switch (priorityJob.getJobState()) {
          case ERROR:
          case DROPPED:
          case STOPPED:
          case GIVEN_UP:
            status = Status.FAILED;
            break;
          case SUCCEEDED:
            status = Status.SUCCESS;
            break;
          case ACTIVE:
            status = Status.ACTIVE;
            break;
          case QUEUED:
            status = Status.WAITING;
            break;
          default:
            status = Status.WARN;
            break;
        }
      } else {

        for (ConversionJobBucket bucket : getBuckets()) {
          Status bucketStatus = bucket.getStatus();
          if (bucketStatus == Status.FAILED) { // Any failed bucket sets overall status.
            status = Status.FAILED;
            break;
          } else {
            // In case of unknown, priority over Success.
            status = bucketStatus == Status.WARN ? bucketStatus : status;
          }
        }
      }
    }
    return status;
  }

  private Job getPriorityJob() {
    Job job = null;
    for (ConversionJobBucket bucket : getBuckets()) {
      String id = bucket.getJobId();
      Job j = jobManager.getJobById(id);

      if (j != null) {
        if (job == null) {
          job = j;
        } else if (JOB_STATE_PRIORITY.indexOf(j.getJobState()) < JOB_STATE_PRIORITY.indexOf(job.getJobState())) {
          job = j;
        }
      }
    }
    return job;
  }

  public enum Type {

    FULL(FullConversionJobExecutor.JOB_TOPIC),
    COMPONENT(ComponentJobExecutor.JOB_TOPIC),
    STRUCTURE(PageStructureJobExecutor.JOB_TOPIC),
    POLICY(PolicyJobExecutor.JOB_TOPIC),
    FORM(FormConversionJobExecutor.JOB_TOPIC);

    private final String topic;

    Type(String topic) {
      this.topic = topic;
    }

    public String getTopic() {
      return this.topic;
    }
  }

  public enum PageHandling {
    NONE,
    RESTORE,
    COPY
  }

  public enum Status {
    FAILED,
    ACTIVE,
    WAITING,
    WARN,
    SUCCESS,
    UNKNOWN
  }

}
