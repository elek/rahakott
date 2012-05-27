<#include "default.html"> 
<#macro page_head> 
        <title>Summary</title>
        <script type="text/javascript">
            $(document).ready(function() {
                $('.table-striped').dataTable(
                    {
                        "bFilter": false,
                        "bPaginate": false,
                        "bInfo": false,
                    }
                );
            } );
        </script>  
</#macro>
 
<#macro page_body> 
    <h1>Yearly reports</h1>                       
    <#list root?keys as year>
       <h2>${year}</h2>
       <#list root[year]?keys as type>
          <h2>${type}</h2>
          <table class="table-striped span12 table-bordered">
             <thead>
             <tr>
                <th></th>          
                <#list 1..12 as m>
                   <th width="50px">${m?string("00")}</th>
                </#list>
             </tr>
             </thead>
             <tbody>
               
               <#list root[year][type]?keys as account>
                  <tr> 
                    <td><a href="${account}.html">${account}</a></td>
                    <#list 1..12 as month>                    
                       <#assign t = root[year][type][account]>
                        <#if t[month?string("00")]?? >                          
                          <td><a href="${account}.html#${year}.${month?string("00")}">${t[month?string("00")]?string("0")}</a></td>
                       <#else>
                          <td></td>                          
                       </#if>
                      
                    </#list>               
                  </tr>
               </#list>
              </tr>
              </tbody>
              <tfoot>
              <tr>
                <th>Actual balance</th>          
                <#list 1..12 as m>
                   <th width="50px">${sum[year][type][m?string("00")][0]?string("0")}</th>
                </#list>
              </tr>
              <tr>
                <th>Change</th>                         
                <#list 1..12 as m>
                   <th width="50px">${sum[year][type][m?string("00")][1]?string("0")}</th>
                </#list>
              </tr>
              </tfoot>
              </table>                  
             </#list>
               
    </#list>       
</#macro>
<@page_html/> 