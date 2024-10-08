package com.adobe.aem.modernize.servlet;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;

import com.adobe.aem.modernize.MockStyle;
import com.adobe.aem.modernize.policy.PolicyImportRuleService;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.designer.Cell;
import com.day.cq.wcm.api.designer.Design;
import com.day.cq.wcm.api.designer.Designer;
import com.day.cq.wcm.api.designer.Style;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import jakarta.servlet.http.HttpServletResponse;
import mockit.Delegate;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static jakarta.servlet.http.HttpServletResponse.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AemContextExtension.class)
public class ListDesignsServletTest {

  private final AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

  @Mocked
  private Designer designer;

  @Mocked
  private Design design;

  @Mocked
  private Cell cell;

  @Injectable
  private PolicyImportRuleService policyImportRuleService;

  @Tested
  private ListDesignsServlet servlet;

  @BeforeEach
  public void beforeEach() {
    context.load().json("/servlet/page-content.json", "/content/test");
    context.load().json("/servlet/design-content.json", "/etc/designs/test");
  }

  @Test
  public <R extends ResourceResolver> void testNoStyle() throws Exception {
    MockSlingHttpServletRequest request = new MockSlingHttpServletRequest(context.resourceResolver(), context.bundleContext());
    MockSlingHttpServletResponse response = new MockSlingHttpServletResponse();

    new MockUp<R>() {
      @Mock
      public <T> T adaptTo(Invocation inv, Class<T> clazz) {
        if (clazz == Designer.class) {
          return (T) designer;
        } else {
          return inv.proceed();
        }
      }
    };
    new Expectations() {{
      designer.getStyle(withInstanceOf(Resource.class));
      result = null;
    }};
    Map<String, Object> params = new HashMap<>();
    params.put("path", "/content/test");
    request.setParameterMap(params);

    servlet.doGet(request, response);

    assertEquals(SC_OK, response.getStatus(), "Request Status");
    AbstractListConversionPathsServlet.ResponseData result = new ObjectMapper().readValue(response.getOutputAsString(), AbstractListConversionPathsServlet.ResponseData.class);
    assertEquals(0, result.getTotal(), "Correct number of paths");

  }

  @Test
  public <R extends ResourceResolver> void testDoGetSuccessSuper() throws Exception {
    MockSlingHttpServletRequest request = new MockSlingHttpServletRequest(context.resourceResolver(), context.bundleContext());
    MockSlingHttpServletResponse response = new MockSlingHttpServletResponse();

    Resource resource = context.resourceResolver().getResource("/etc/designs/test/jcr:content/homepage");
    Style homepageStyle = new MockStyle(design, cell, "/etc/designs/test/jcr:content/homepage",  resource);

    resource = context.resourceResolver().getResource("/etc/designs/test/jcr:content/page");
    Style pageStyle = new MockStyle(design, cell, "/etc/designs/test/jcr:content/page", resource);

    Style baseStyle = new MockStyle(design, cell, "/etc/designs/test/jcr:content/basepage");


    Set<String> homepagePaths = new HashSet<>();
    homepagePaths.add("/etc/designs/test/jcr:content/homepage/title");
    homepagePaths.add("/etc/designs/test/jcr:content/homepage/rightpar/title");

    Set<String> pagePaths = new HashSet<>();
    homepagePaths.add("/etc/designs/test/jcr:content/page/title");
    homepagePaths.add("/etc/designs/test/jcr:content/page/par/title");
    homepagePaths.add("/etc/designs/test/jcr:content/page/rightpar/title");

    List<Resource> capture = new ArrayList<>();

    new MockUp<R>() {
      @Mock
      public <T> T adaptTo(Invocation inv, Class<T> clazz) {
        if (clazz == Designer.class) {
          return (T) designer;
        } else {
          return inv.proceed();
        }
      }
    };
    new Expectations() {{
      designer.getDesign(withInstanceOf(Page.class));
      result = design;
      designer.getStyle(withInstanceOf(Resource.class));
      result = homepageStyle;
      cell.paths();
      result = new Delegate<Iterator<String>>() {
        Iterator<String> delegate() {
          return Arrays.stream(new String[] { "homepage", "page", "basepage" }).iterator();
        }
      };
      design.getStyle("homepage");
      result = homepageStyle;
      design.getStyle("page");
      result = pageStyle;
      design.getStyle("basepage");
      result = baseStyle;
      policyImportRuleService.find(withCapture(capture));
      returns(homepagePaths, pagePaths);
    }};

    Map<String, Object> params = new HashMap<>();
    params.put(AbstractListConversionPathsServlet.PARAM_PATH, "/content/test");
    params.put(ListDesignsServlet.PARAM_INCLUDE_SUPER_TYPES, "true");
    request.setParameterMap(params);

    servlet.doGet(request, response);

    assertEquals("/etc/designs/test/jcr:content/homepage", capture.get(0).getPath(), "Resource search path");
    assertEquals("/etc/designs/test/jcr:content/page", capture.get(1).getPath(), "Resource search path");
    assertEquals(SC_OK, response.getStatus(), "Request Status");
    AbstractListConversionPathsServlet.ResponseData result = new ObjectMapper().readValue(response.getOutputAsString(), AbstractListConversionPathsServlet.ResponseData.class);
    assertEquals(5, result.getTotal(), "Correct number of paths");
    assertTrue( result.getPaths().contains("/etc/designs/test/jcr:content/homepage/title"), "Path content");
    assertTrue( result.getPaths().contains("/etc/designs/test/jcr:content/homepage/rightpar/title"), "Path content");
    assertTrue( result.getPaths().contains("/etc/designs/test/jcr:content/page/title"), "Path content");
    assertTrue( result.getPaths().contains("/etc/designs/test/jcr:content/page/par/title"), "Path content");
    assertTrue( result.getPaths().contains("/etc/designs/test/jcr:content/page/rightpar/title"), "Path content");
  }

  @Test
  public <R extends ResourceResolver> void testDoGetSuccessShallow() throws Exception {
    MockSlingHttpServletRequest request = new MockSlingHttpServletRequest(context.resourceResolver(), context.bundleContext());
    MockSlingHttpServletResponse response = new MockSlingHttpServletResponse();

    Resource resource = context.resourceResolver().getResource("/etc/designs/test/jcr:content/homepage");
    Style style = new MockStyle(design, cell, "/etc/designs/test/jcr:content/homepage", resource);

    Set<String> paths = new HashSet<>();
    paths.add("/etc/designs/test/jcr:content/homepage/title");
    paths.add("/etc/designs/test/jcr:content/homepage/rightpar/title");
    List<Resource> capture = new ArrayList<>();

    new MockUp<R>() {
      @Mock
      public <T> T adaptTo(Invocation inv, Class<T> clazz) {
        if (clazz == Designer.class) {
          return (T) designer;
        } else {
          return inv.proceed();
        }
      }
    };
    new Expectations() {{
      designer.getDesign(withInstanceOf(Page.class));
      result = design;
      designer.getStyle(withInstanceOf(Resource.class));
      result = style;
      cell.paths();
      result = Arrays.stream(new String[] { "homepage", "page", "basepage" }).iterator();
      design.getStyle("homepage");
      result = style;
      policyImportRuleService.find(withCapture(capture));
      result = paths;
    }};

    Map<String, Object> params = new HashMap<>();
    params.put("path", "/content/test");
    request.setParameterMap(params);

    servlet.doGet(request, response);

    assertEquals("/etc/designs/test/jcr:content/homepage", capture.get(0).getPath(), "Resource search path");
    assertEquals(SC_OK, response.getStatus(), "Request Status");
    AbstractListConversionPathsServlet.ResponseData result = new ObjectMapper().readValue(response.getOutputAsString(), AbstractListConversionPathsServlet.ResponseData.class);
    assertEquals(2, result.getTotal(), "Correct number of paths");
    assertTrue( result.getPaths().contains("/etc/designs/test/jcr:content/homepage/title"), "Path content");
    assertTrue( result.getPaths().contains("/etc/designs/test/jcr:content/homepage/rightpar/title"), "Path content");
  }


}
