<a id="top"></a>

<p style="font-size: 24px;"><img src="./qct-icons/transform-logo.svg" style="margin-right: 15px; vertical-align: middle;"></img><b>Code Transformation Summary by Amazon Q </b></p>
<p><img src="./qct-icons/transform-variables-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Lines of code in your application: 11194 <p>
<p><img src="./qct-icons/transform-clock-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Transformation duration: 37 min(s) <p>
<p><img src="./qct-icons/transform-dependencies-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Planned dependencies replaced: 5 of 13 <p>
<p><img src="./qct-icons/transform-dependencyAnalyzer-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Additional dependencies added: 3 <p>
<p><img src="./qct-icons/transform-smartStepInto-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Planned deprecated code instances replaced: 0 of 0 <p>
<p><img src="./qct-icons/transform-listFiles-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Files changed: 44 <p>
<p><img src="./qct-icons/transform-build-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Build status in Java 17: <span style="color: #CCCC00">PARTIALLY_SUCCEEDED</span> <p>

### Table of Contents

1. <a href="#build-log-summary">Build log summary</a> 
1. <a href="#planned-dependencies-replaced">Planned dependencies replaced</a> 
1. <a href="#additional-dependencies-added">Additional dependencies added</a> 
1. <a href="#deprecated-code-replaced">Deprecated code replaced</a> 
1. <a href="#other-changes">Other changes</a> 
1. <a href="#all-files-changed">All files changed</a> 
1. <a href="#next-steps">Next steps</a> 


### Build log summary <a style="float:right; font-size: 14px;" href="#top">Scroll to top</a><a id="build-log-summary"></a>

Amazon Q could not build the upgraded code in Java 17. The following build log snippet that shows the errors Amazon Q encountered during the build log. To view the full build log, open [`buildCommandOutput.log`](./buildCommandOutput.log)

```
The Maven build failed while compiling the core module due to compilation errors related to overriding methods in servlet classes. Six errors were reported where servlet classes were overriding the doGet method but not declaring that they throw ServletException. The build failed at the compile phase for the core module.
```


### Planned dependencies replaced <a style="float:right; font-size: 14px;" href="#top">Scroll to top</a><a id="planned-dependencies-replaced"></a>

Amazon Q updated the following dependencies that it identified in the transformation plan

| Dependency | Action | Previous version in Java 11 | Current version in Java 17 |
|--------------|--------|--------|--------|
| `jakarta.annotation:jakarta.annotation-api` | Added | - | 3.0.0 |
| `jakarta.inject:jakarta.inject-api` | Added | - | 2.0.1 |
| `jakarta.servlet:jakarta.servlet-api` | Added | - | 6.1.0 |
| `org.jacoco:jacoco-maven-plugin` | Updated | - | 0.8.12 |
| `org.projectlombok:lombok` | Updated | - | 1.18.34 |

### Additional dependencies added <a style="float:right; font-size: 14px;" href="#top">Scroll to top</a><a id="additional-dependencies-added"></a>

Amazon Q updated the following additional dependencies during the upgrade

| Dependency | Action | Previous version in Java 11 | Current version in Java 17 |
|--------------|--------|--------|--------|
| `org.junit.jupiter:junit-jupiter-api` | Updated | 5.10.2 | 5.10.3 |
| `org.junit.jupiter:junit-jupiter-engine` | Updated | 5.10.2 | 5.10.3 |
| `org.slf4j:slf4j-simple` | Updated | 1.7.36 | 2.0.13 |

### Deprecated code replaced <a style="float:right; font-size: 14px;" href="#top">Scroll to top</a><a id="deprecated-code-replaced"></a>

Amazon Q replaced the following instances of deprecated code. An instance with 0 files
changed indicates Amazon Q wasn't able to replace the deprecated code.

| Deprecated code | Files changed |
|----------------|----------------|


### Other changes <a style="float:right; font-size: 14px;" href="#top">Scroll to top</a><a id="other-changes"></a>



### All files changed <a style="float:right; font-size: 14px;" href="#top">Scroll to top</a><a id="all-files-changed"></a>

| File | Action |
|----------------|--------|
| [all/pom.xml](../all/pom.xml) | Updated |
| [core/pom.xml](../core/pom.xml) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/component/rule/ColumnControlRewriteRule.java](../core/src/main/java/com/adobe/aem/modernize/component/rule/ColumnControlRewriteRule.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/impl/RewriteUtils.java](../core/src/main/java/com/adobe/aem/modernize/impl/RewriteUtils.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/job/datasource/ConversionJobBucketDataSource.java](../core/src/main/java/com/adobe/aem/modernize/job/datasource/ConversionJobBucketDataSource.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/job/datasource/ConversionJobDataSource.java](../core/src/main/java/com/adobe/aem/modernize/job/datasource/ConversionJobDataSource.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/model/ConversionJob.java](../core/src/main/java/com/adobe/aem/modernize/model/ConversionJob.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/model/ConversionJobBucket.java](../core/src/main/java/com/adobe/aem/modernize/model/ConversionJobBucket.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/model/ConversionJobNavItem.java](../core/src/main/java/com/adobe/aem/modernize/model/ConversionJobNavItem.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/policy/impl/PolicyImportRuleServiceImpl.java](../core/src/main/java/com/adobe/aem/modernize/policy/impl/PolicyImportRuleServiceImpl.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/policy/impl/PolicyTreeImporter.java](../core/src/main/java/com/adobe/aem/modernize/policy/impl/PolicyTreeImporter.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/rule/impl/AbstractRewriteRuleService.java](../core/src/main/java/com/adobe/aem/modernize/rule/impl/AbstractRewriteRuleService.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/rule/impl/NodeBasedRewriteRule.java](../core/src/main/java/com/adobe/aem/modernize/rule/impl/NodeBasedRewriteRule.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/AbstractListConversionPathsServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/AbstractListConversionPathsServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/AbstractRulesServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/AbstractRulesServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/ComponentRulesServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/ComponentRulesServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/ListChildrenServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/ListChildrenServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/ListComponentsServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/ListComponentsServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/ListDesignsServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/ListDesignsServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/PolicyRulesServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/PolicyRulesServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/ScheduleConversionJobServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/ScheduleConversionJobServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/TemplateRulesServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/TemplateRulesServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/rule/AbstractListRulesServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/rule/AbstractListRulesServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/rule/ListComponentRulesServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/rule/ListComponentRulesServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/rule/ListPolicyRulesServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/rule/ListPolicyRulesServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/rule/ListTemplateRulesServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/rule/ListTemplateRulesServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/servlet/rule/PageInfoServlet.java](../core/src/main/java/com/adobe/aem/modernize/servlet/rule/PageInfoServlet.java) | Updated |
| [core/src/main/java/com/adobe/aem/modernize/structure/rule/PageRewriteRule.java](../core/src/main/java/com/adobe/aem/modernize/structure/rule/PageRewriteRule.java) | Updated |
| [core/src/test/java/com/adobe/aem/modernize/job/datasource/ConversionJobBucketDataSourceTest.java](../core/src/test/java/com/adobe/aem/modernize/job/datasource/ConversionJobBucketDataSourceTest.java) | Updated |
| [core/src/test/java/com/adobe/aem/modernize/job/datasource/ConversionJobDataSourceTest.java](../core/src/test/java/com/adobe/aem/modernize/job/datasource/ConversionJobDataSourceTest.java) | Updated |
| [core/src/test/java/com/adobe/aem/modernize/servlet/AbstractListConversionPathsServletTest.java](../core/src/test/java/com/adobe/aem/modernize/servlet/AbstractListConversionPathsServletTest.java) | Updated |
| [core/src/test/java/com/adobe/aem/modernize/servlet/AbstractRulesServletTest.java](../core/src/test/java/com/adobe/aem/modernize/servlet/AbstractRulesServletTest.java) | Updated |
| [core/src/test/java/com/adobe/aem/modernize/servlet/ListChildrenServletTest.java](../core/src/test/java/com/adobe/aem/modernize/servlet/ListChildrenServletTest.java) | Updated |
| [core/src/test/java/com/adobe/aem/modernize/servlet/ListComponentsServletTest.java](../core/src/test/java/com/adobe/aem/modernize/servlet/ListComponentsServletTest.java) | Updated |
| [core/src/test/java/com/adobe/aem/modernize/servlet/ListDesignsServletTest.java](../core/src/test/java/com/adobe/aem/modernize/servlet/ListDesignsServletTest.java) | Updated |
| [core/src/test/java/com/adobe/aem/modernize/servlet/ScheduleConversionJobServletTest.java](../core/src/test/java/com/adobe/aem/modernize/servlet/ScheduleConversionJobServletTest.java) | Updated |
| [core/src/test/java/com/adobe/aem/modernize/servlet/rule/AbstractListRulesServletTest.java](../core/src/test/java/com/adobe/aem/modernize/servlet/rule/AbstractListRulesServletTest.java) | Updated |
| [core/src/test/java/com/adobe/aem/modernize/servlet/rule/ListComponentRulesServletTest.java](../core/src/test/java/com/adobe/aem/modernize/servlet/rule/ListComponentRulesServletTest.java) | Updated |
| [core/src/test/java/com/adobe/aem/modernize/servlet/rule/ListPolicyRulesServletTest.java](../core/src/test/java/com/adobe/aem/modernize/servlet/rule/ListPolicyRulesServletTest.java) | Updated |
| [core/src/test/java/com/adobe/aem/modernize/servlet/rule/ListTemplateRulesServletTest.java](../core/src/test/java/com/adobe/aem/modernize/servlet/rule/ListTemplateRulesServletTest.java) | Updated |
| [pom.xml](../pom.xml) | Updated |
| [ui.apps.structure/pom.xml](../ui.apps.structure/pom.xml) | Updated |
| [ui.apps/pom.xml](../ui.apps/pom.xml) | Updated |
| [ui.config/pom.xml](../ui.config/pom.xml) | Updated |

### Next steps <a style="float:right; font-size: 14px;" href="#top">Scroll to top</a><a id="next-steps"></a>

1. Please review and accept the code changes using the diff viewer.If you are using a Private Repository, please ensure that updated dependencies are available.
1. 
1. In order to successfully verify these changes on your machine, you will need to change your project to Java 17. We verified the changes using [Amazon Corretto Java 17](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/what-is-corretto-17.html
) build environment.
1. If this project uses Maven CheckStyle, Enforcer, FindBugs or SpotBugs plugins, Q Code Transformation will disable those plugins when we build the project to verify proposed upgrades.