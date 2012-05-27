<#include "default.html"> 
<#macro page_head> 
        <title>Rahakott summary</title>
         <script type="text/javascript">
    
        // Load the Visualization API and the piechart package.
        google.load('visualization', '1.0', {'packages':['corechart']});
      
        // Set a callback to run when the Google Visualization API is loaded.
        google.setOnLoadCallback(drawChart);
      
   

        // Callback that creates and populates a data table, 
        // instantiates the pie chart, passes in the data and
        // draws it.
        function drawChart() {
<#list root.groups as ag>       
        var data_${ag.type} = new google.visualization.DataTable();
        data_${ag.type}.addColumn('string', 'Account');
        data_${ag.type}.addColumn('number', 'Balance');
        data_${ag.type}.addRows([
        <#list ag.accounts as account>
          ['${account.name}', Math.abs(${account.getNormalBalance(root)?string("0")})],
        </#list>        
        ]);

        // Set chart options
        var options_${ag.type} = {'title':'${ag.type}',
                       'width':400,
                       'height':300};

        // Instantiate and draw our chart, passing in some options.
        var chart_${ag.type} = new google.visualization.PieChart(document.getElementById('chart_${ag.type}'));
        chart_${ag.type}.draw(data_${ag.type}, options_${ag.type});
</#list>        
      
        }
        </script> 
</#macro>
 
<#macro page_body> 
       <#list root.groups as ag>
          <div class="row">
          <div class="span4">
          <h1>${ag.type}</h1>
          <table class="table-striped span4">
          <#list ag.accounts as account>
          <tr>
             <td><a href="${account.name}.html">${account.name}</a></td>
             <td>${account.getNormalBalance(root)}</td>
          </tr>         
           </#list>   
          </table>
          </div>
          <div class="span4">
             <div id="chart_${ag.type}"></div>
          </div>
          </div>   
       </#list>
       
</#macro>
<@page_html/> 