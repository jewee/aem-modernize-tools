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
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import jakarta.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import lombok.Getter;
import lombok.Setter;

@Model(
    adaptables = Resource.class
)
@Getter
@Setter
public class ConversionJobBucket {
  public static final String RESOURCE_TYPE = "aem-modernize/components/job/bucket";
  public static final String PN_JOB_ID = "jobId";
  public static final String PN_PATHS = "paths";
  public static final String PN_SUCCESS = "success";
  public static final String PN_FAILED = "failed";
  public static final String PN_NOT_FOUND = "notFound";
  public static final String PN_STARTED = "started";
  public static final String PN_FINISHED = "finished";
  public static final String PN_JOB_STATUS = "jobStatus";

  @Self
  @Required
  private Resource resource;

  @ValueMapValue
  @Named(PN_JOB_ID)
  @Optional
  private String jobId;

  @ValueMapValue
  @Named(PN_PATHS)
  @Required
  private List<String> paths;

  @ValueMapValue
  @Named(PN_SUCCESS)
  @Optional
  private List<String> success = new ArrayList<>();

  @ValueMapValue
  @Named(PN_FAILED)
  @Optional
  private List<String> failed = new ArrayList<>();

  @ValueMapValue
  @Named(PN_NOT_FOUND)
  @Optional
  private List<String> notFound = new ArrayList<>();

  @ValueMapValue
  @Named(PN_STARTED)
  @Optional
  private Calendar started;

  @ValueMapValue
  @Named(PN_FINISHED)
  @Optional
  private Calendar finished;

  private ConversionJob.Status status;

  public ConversionJob.Status getStatus() {
    if (status == null) {
      status = ConversionJob.Status.UNKNOWN;
      if (!failed.isEmpty()) {
        status = ConversionJob.Status.FAILED;
      } else if (!notFound.isEmpty()) {
        status = ConversionJob.Status.WARN;
      } else if (!success.isEmpty()) {
        status = ConversionJob.Status.SUCCESS;
      }

      ValueMap vm = resource.getValueMap();
      String str = vm.get(PN_JOB_STATUS, String.class);
      try {
        if (StringUtils.isNotBlank(str)) {
          status = ConversionJob.Status.valueOf(str);
        }
      } catch (IllegalArgumentException e) {
        // Do nothing;
      }
    }
    return status;
  }
}
