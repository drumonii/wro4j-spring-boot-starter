# wro4j-spring-boot-starter
A Spring Boot starter and auto-configuration for wro4j:

> [Wro4j](http://alexo.github.io/wro4j/) is a tool for analysis and optimization of web resources. It brings together almost all the modern web tools: JsHint, CssLint, JsMin, Google Closure compressor, YUI Compressor, UglifyJs, Dojo Shrinksafe, Css Variables Support, JSON Compression, Less, Sass, CoffeeScript and much more.

## Introduction

This starter does the following auto configuration for you:

* Creating the _WroFilter_ and the _WroModelFactory_
* Registering them as a ServletFilter through a Spring _FilterRegistrationBean_

Furthermore it provides a Spring Based caching strategy if a [CacheManager](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/cache/CacheManager.html) is present (which is the case if you're Spring Boot Application is configured with [@EnableCaching](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/cache/annotation/EnableCaching.html)) and a cache name is configured.

The wro4j-spring-boot-starter expecteds the Wro4j model configuration in xml format. If you want the Groovy version you have to role your own _WroModelFactory_, which is recognised by the auto configuration.

This starter doesn't bring in the _wro4j-extension_ artifact which are a lot of third party libraries. If you need them, you have to include this in your Maven or Gradle build file.

With the starter comes an additional resource processor _removeSourceMaps_ which removes stale source maps from minified and concatened files.

This starter is in production at [euregjug.eu](http://www.euregjug.eu) and in will be in upcoming [ENERKO Informatik](http://www.enerko-informatik.de) products.

## Usage and configuration

Just include the starter in your pom.xml:

```
<dependency>
    <groupId>eu.michael-simons</groupId>
    <artifactId>wro4j-spring-boot-starter</artifactId>
    <version>0.0.7</version>	    
</dependency>
```

Add a wro.xml to your resources:

```
<?xml version="1.0" encoding="UTF-8"?>
<groups xmlns="http://www.isdc.ro/wro">
</groups>
```

including your CSS and JS files and you're pretty much good to go. Be aware that this file belongs to your resources (i.e. /src/main/resources) and not under your WEB-INF directory as without the starter. I prefer having those configuration in one place. Read more about the wro.xml format at the official [Wro4j documentation](http://wro4j.readthedocs.org/en/stable/GettingStarted/#step-3-create-wroxml-under-web-inf-directory-and-organize-your-resources-in-groups).

To actually minify your resources, you have to configure some processors. The starter is used at [euregjug.eu](http://www.euregjug.eu) for example with the following configuration:

```
wro4j.managerFactory.preProcessors = removeSourceMaps, cssUrlRewriting, cssImport, cssMinJawr, semicolonAppender, jsMin
```

Have a look at the configuration of the JUGs site at the source [here](https://github.com/EuregJUG-Maas-Rhine/site). If you're already looking for a solution to use Wro4j with Spring Boot and found this starter than i guess you already know about the runtime solution Wro4j provides for your CSS and JS resources.

You can use all processors as described [here](http://wro4j.readthedocs.org/en/stable/AvailableProcessors/).

For further configuration you can use all properties described under [Available Configuration Options](http://wro4j.readthedocs.org/en/stable/ConfigurationOptions/) under the namespace _wro4j.*_, the options for configuring the pre- and postprocessors are under the subnamespace _wro4j.managerFactory.*_, as _wro4j.managerFactory.preProcessors_ and _wro4j.managerFactory.postProcessors_.

As an alternative, you can add processors via their fully qualified classname as _wro4j.preProcessors_ and _wro4j.postProcessors_. Configuring the processors via name or fully qualified class are mutually exclusive.

### Options not present in the original Wro4j version

<table>
        <thead>
                <tr>
                        <th>Option</th>
                        <th>Default</th>
                        <th>Meaning</th>
                </tr>
        </thead>
        <tfoot />
        <tbody>
                <tr>
                        <td>wro4j.model</td>
                        <td>/wro.xml</td>
                        <td>The resource containing the Wro4j model definition (will be looked up as a classloader resource, not inside WEB-INF)</td>
                </tr>
                <tr>
                        <td>wro4j.filterUrl</td>
                        <td>/wro4j</td>
                        <td>Url to which the filter is mapped. Will be expanded to <em>value/*</em></td>
                </tr>                                
                <tr>
                        <td>wro4j.cacheName</td>
                        <td></td>
                        <td>The name of a Spring Cache. If this property is set and a CacheManager is configured (for example through @EnableCaching), then a CacheStrategy based on Spring cache abstraction will be used.</td>
                </tr>
        </tbody>
</table>

### Not configurable at the moment

The options _uriLocators_, _namingStrategy_ and _hashStrategy_ are not configurable at the moment through this starter. If you need those, you have to provide your own _WroManagerFactory_ as a Spring Bean, configured to your needs. The starter will still configure the model and processors factories for you and pass them to your manager factory, though.

## Acknowledgements

I've been using Wro4j as a runtime solution since 2012 on [dailyfratze.de](https://dailyfratze.de) and it really worked well for me. Thanks [Alex](https://twitter.com/wro4j) for your work.

As always, the Spring documentation is a valuable resource. Here's how to start your own auto-configuration or starter: [Creating your own auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html). Thanks to all the people involved.

There's a another Wro4j [starter](https://github.com/sbuettner/spring-boot-autoconfigure-wro4j) by Simon Buettner from which i had the basic idea, but i didn't like the fact that it centers around the Groovy model and especially the whole wro4-extensions.

## Examples

* [euregjug.eu](http://www.euregjug.eu), Source code here [github.com/EuregJUG-Maas-Rhine/site](https://github.com/EuregJUG-Maas-Rhine/site). Pretty standard site that uses a theme from [html5up](http://html5up.net), some JQuery.
* [biking2](http://biking.michael-simons.eu), Source code here [github.com/michael-simons/biking2](https://github.com/michael-simons/biking2). A full blown AngularJS application.
 
[ENERKO Informatik GmbH](http://www.enerko-informatik.de) is using Wro4j and this starter in several products as well.