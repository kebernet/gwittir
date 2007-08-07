<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<html>
  <head>
    <title></title>
    
  </head>
  <body>
      <script type="text/javascript">
          com_totsp_gwittir_example_client_remote_LookupFreezer = {
            stateLookups : unescape( "<%= application.getAttribute( "StateLookups" ) %>" ),
            typeLookups : unescape( "<%= application.getAttribute( "TypeLookups" ) %>" )
          };
          
      </script>
  <iframe id="__gwt_historyFrame" style="width:0;height:0;border:0"></iframe>
      <script src="com.totsp.gwittir.example.Contacts.nocache.js"></script>
  </body>
</html>

