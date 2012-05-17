<#include "default.html"> 
<#macro page_head> 
        <title>Account ${root.name}</title>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#table').dataTable(
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
    <h1>${root.name}</h1>
        <ul>
          <li>Currency: ${root.currency}</li>
          <li>Type: ${root.type}</li>
          <li>Managed: ${root.managed?string}</li>
          <li>Opening balance: ${root.openingBalance?string}</li>
        </ul>
       <table id="table" class="table-striped span12 table-bordered">
          <thead>
             <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Amount</th>
                <th></th>
                <th>Converted</th>
                <th></th>
                <th>Balance</th>
                <th></th>
              </tr>
          </thead>
          <#assign balance=root.openingBalance>
          <tbody>                   
          <#list root.transactions as t>
          <tr>
             <#assign balance = root.nextBalance(t,balance)>
             <td>${t.date?date}</td>
             <td>${t.description}</td>
             <td>${root.getAmount(t)}</td>
             <td>${t.currency}</td>
             <td>${t.getNormalAmount(root)}</td>
             <td>${root.currency}</td>
             <td>${balance}</td>
             <td><a href="${t.getOtherSide(root).name}.html"><span class="label label-info">${t.getOtherSide(root).name}</span></a></td>
             
          </tr>
          </#list>
          </tbody>
          </table>       
</#macro>
<@page_html/> 