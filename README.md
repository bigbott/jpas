# JPaS
Java nano framework for creating traditional multi-page web apps

**JPaS** stands for Java Pages Simplified, it serves `GET `requests and returns `HTML `in response.

### Why was it written?
I needed a simple Java backend for my multi-page app. Though I could use Struts or Spring MVC, I wanted something simpler, smaller, predictable, and hopefully more performant. Also, I'm not too fond of templates as a phenomenon, no matter if JSP, Velocity, or Theamleaf, JPaS parses plain HTML templates at server start time using JSoup and modifies Document (Java object) at request time. 

### Example
Here is the server-code we required to write to process request in JPaS:

`public class SmartphoneView implements JpasView {`
      
    public String createHtml(HttpServletRequest request, HttpServletResponse response, Map<String, String[]> params, Document doc, JpasModel model) {`
        //String smartphoneId = params.get("id")[0]; //first and second ways of processing parameters
        String smartphoneId = params.get("param1")[0]; //third way of processing parameters
        Smartphone phone = ((SmartphoneModel) model).getSmartphoneById(smartphoneId);
        doc.select("head title").first().text(phone.title);
        doc.select("#title h2").first().text(phone.title);         
        return doc.toString();
    }`
}`

### Dependencies
   [JSoup](https://jsoup.org/)

### Conventions
* Only GET requests are served. To process non-idempotent requests (POST) use [JRPC](https://github.com/bigbott/jrpc) or any other JSON-RPC or REST framework. 
* Templates stored in `\resources\templates\` folder. `\resources\` should be added to project sources. After deploy `\templates\` folder should be in `WEB-INF\classes\`  
* For every HTML template two Java classes should be created: 

     example 1: Template: `smartphone.html`, classes `SmartphoneView `and `SmartphoneModel`

     example 2: Template: `user-list.html`, Classes `UserListView `and `UserListModel `
* JPaS supports three ways of passing parameters in URL: 
    1. `/jpas/demo?page=smartphone&id=000001` - the traditional way of putting parameters in the query. Page parameter defines the template. 
    2. `/jpas/demo/page/smartphone/id/000001` - "static" URL loved by Google and generally preferable. 
    3. `/jpas/demo/smartphone/000001`   - "static" URL with parameter names omitted. JPaS parses URL (pathInfo) and creates paramsMap where keys are going like `param0`, `param1` `param2`, and so on. 
    In the last two examples browser will treat 000001 as a document, as result only absolute and relative to root paths will work:
    `https://example.com/jpas/css/main.css` - will work;
                       `/jpas/css/main.css` - will work;
                             `css/main.css` - will not. But we should never use it anyway.  

### Configuration
JPaS needs to know the packages where the `View `and `Model `classes are. We pass this info as init parameters to servlet: 
`<servlet>

        <servlet-name>jpasServlet</servlet-name>

        <servlet-class>me.jpas.JpasServletController</servlet-class>

        <load-on-startup>1</load-on-startup>

        <init-param>

            <param-name>viewPackage</param-name>

            <param-value>demo.view</param-value>

        </init-param>

        <init-param>

            <param-name>modelPackage</param-name>

            <param-value>demo.model</param-value>

        </init-param>

    </servlet>`


### Size
JPaS is really small - less than 200 lines of code.

### Performance
Blazingly fast. The request processing time is 3 ms. (Without network and DB latencies obviously).

### Demo
The demo is [here](https://phoneparator.com/jpas). 

### Getting started
The best way to start with JPaS is to download the project from github. If you don't use Netbeans your IDE should have 
"Create a project from existing sources" option. Otherwise, please, change IDE or just copy-paste the code manually. 
The project contains full demo code (client and server). 

### Applicability
As JPaS only serves GET requests, it should be combined with JRPC or any other RPC or REST framework which able to process AJAX POST (at least) requests to create a fully functional Java backend.
Though, it is possible to think about cases when JPaS can be used standalone: for example customization (e.g. localization) of SPA. 

### More simple Java frameworks
**[JRPC](https://github.com/bigbott/jrpc)** is a Java JSON-RPC over HTTP web(*) mini framework. 
*It can be used for creating back-ends for SPA web apps, mobile apps, desktop apps - any app that sends/receives JSON over HTTP.

**[JSId](https://github.com/bigbott/JSId)** - stands for Java Simple Identification. It is an authentication framework that uses custom encrypted JWT (JSON Web Tokens) for authentication. 
       It consists of a JQuery plugin that renders login/register dialogs and a Java Servlet Filter that check credentials and generates tokens. 


### License
MIT license.
