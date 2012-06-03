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
        
       <#assign balance=root.openingBalance>
       
       <#list monthly?keys as month>
       <h2><A name="${month}">${month}</a></h2>
       <table class="table-striped  table-bordered">
          <thead>
             <tr>
                <th width="70px">Date</th>
                <th width="500px">Description</th>
                <th width="70px">Amount</th>
                <th width="40px"></th>
                <th width="70px">Converted</th>
                <th width="40px"></th>
                <th width="70px">Balance</th>
                <th width="80px"></th>
              </tr>
          </thead>          
          <tbody>                 
          <#assign mbalance=0>
          <#list monthly[month] as t>
          <tr>
             <#assign balance = root.nextBalance(t,balance)>
             <#assign mbalance = root.nextBalance(t,mbalance)>
             <td>${t.date?string("yyyy.MM.dd")}</td>
             <td>${t.shortDescription}</td>
             <#if t.currency == root.currency>
               <td></td>
               <td></td>
             <#else>
               <td>${root.getAmount(t)}</td>
               <td>${t.currency}</td>
             </#if>             
             <td>${t.getNormalAmount(root)}</td>
             <td>${root.currency}</td>
             <td>${balance}</td>
             <td><a href="${t.getOtherSide(root).name}.html"><span class="label label-info">${t.getOtherSide(root).name}</span></a></td>
             
          </tr>
          </#list>
          </tbody>
          <tfoot>
          <thead>
             <tr>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th>${mbalance}</th>
                <th></th>
                <th></th>
                <th></th>
              </tr>
          </thead>
          </tfoot>
          </table>
          </#list>       
</#macro>
<@page_html/> 