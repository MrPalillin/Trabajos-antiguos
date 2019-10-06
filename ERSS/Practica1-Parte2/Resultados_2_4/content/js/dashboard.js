/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 100.0, "KoPercent": 0.0};
    var dataset = [
        {
            "label" : "KO",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "OK",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.5989795918367347, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)  ", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "65 /wcal/js_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "61 /wcal/week.php"], "isController": false}, {"data": [0.8928571428571429, 500, 1500, "39 /wcal/css_cacher.php"], "isController": false}, {"data": [1.0, 500, 1500, "64 /wcal/js_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "42 /wcal/month.php"], "isController": false}, {"data": [1.0, 500, 1500, "44 /wcal/css_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "41 /wcal/login.php"], "isController": false}, {"data": [1.0, 500, 1500, "43 /wcal/js_cacher.php"], "isController": false}, {"data": [0.4928571428571429, 500, 1500, "38 /wcal/login.php"], "isController": false}, {"data": [1.0, 500, 1500, "63 /wcal/edit_entry.php"], "isController": false}, {"data": [1.0, 500, 1500, "68 /wcal/edit_entry_handler.php"], "isController": false}, {"data": [0.0, 500, 1500, "67 /wcal/edit_entry_handler.php"], "isController": false}, {"data": [0.0, 500, 1500, "69 /wcal/week.php"], "isController": false}, {"data": [1.0, 500, 1500, "62 /wcal/view_entry.php"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 980, 0, 0.0, 50304.43877551021, 8, 194978, 173266.3, 184401.0, 194028.01, 1.3574347253964956, 10.380655797666043, 0.812794341713415], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "90th pct", "95th pct", "99th pct", "Throughput", "Received", "Sent"], "items": [{"data": ["65 /wcal/js_cacher.php", 70, 0, 0.0, 15.971428571428577, 8, 51, 28.799999999999997, 37.250000000000014, 51.0, 0.7980482021114076, 1.8470451552773788, 0.3086202031602709], "isController": false}, {"data": ["61 /wcal/week.php", 70, 0, 0.0, 164292.41428571433, 155443, 173089, 169770.1, 170690.85, 173089.0, 0.2684491691498215, 2.27106948471182, 0.11666003932780328], "isController": false}, {"data": ["39 /wcal/css_cacher.php", 70, 0, 0.0, 338.87142857142845, 10, 2358, 722.3, 2106.75, 2358.0, 6.446265770328759, 27.303370907772354, 2.3921689382079383], "isController": false}, {"data": ["64 /wcal/js_cacher.php", 70, 0, 0.0, 16.042857142857144, 9, 58, 29.0, 36.900000000000006, 58.0, 0.7980300059282229, 3.8802650385904514, 0.3273169946189977], "isController": false}, {"data": ["42 /wcal/month.php", 70, 0, 0.0, 178662.85714285716, 168867, 194599, 188488.2, 190074.7, 194599.0, 0.259307279125764, 6.848852218003334, 0.1093952583811817], "isController": false}, {"data": ["44 /wcal/css_cacher.php", 70, 0, 0.0, 69.97142857142858, 10, 280, 211.2, 248.45000000000005, 280.0, 0.7279079924297568, 3.082946253093609, 0.2743871924588732], "isController": false}, {"data": ["41 /wcal/login.php", 70, 0, 0.0, 175730.81428571424, 121738, 194978, 194124.4, 194318.05, 194978.0, 0.3448887487436196, 9.80404487526113, 0.5126178472537002], "isController": false}, {"data": ["43 /wcal/js_cacher.php", 70, 0, 0.0, 41.72857142857142, 12, 130, 95.5, 108.60000000000002, 130.0, 0.7278701479656029, 1.6853321492445748, 0.25944590235102055], "isController": false}, {"data": ["38 /wcal/login.php", 70, 0, 0.0, 1089.3857142857144, 17, 2591, 2187.0, 2329.7000000000003, 2591.0, 6.73659897988644, 11.30880238908671, 2.842002694639592], "isController": false}, {"data": ["63 /wcal/edit_entry.php", 70, 0, 0.0, 29.82857142857143, 16, 120, 50.9, 66.9, 120.0, 0.7979390374575382, 6.522995783747122, 0.3927356199986321], "isController": false}, {"data": ["68 /wcal/edit_entry_handler.php", 70, 0, 0.0, 52.68571428571429, 18, 377, 69.9, 220.80000000000007, 377.0, 0.8102414519526819, 1.8088007413709284, 0.9360504274023659], "isController": false}, {"data": ["67 /wcal/edit_entry_handler.php", 70, 0, 0.0, 37856.97142857143, 34507, 41443, 39733.7, 40295.2, 41443.0, 0.5565316944800006, 1.6560078838677363, 0.6375114039307038], "isController": false}, {"data": ["69 /wcal/week.php", 70, 0, 0.0, 146019.12857142862, 127338, 160509, 156398.1, 157729.75, 160509.0, 0.3280378649421247, 2.7751875234312764, 0.14672006068700502], "isController": false}, {"data": ["62 /wcal/view_entry.php", 70, 0, 0.0, 45.47142857142859, 16, 280, 93.0, 122.25000000000007, 280.0, 0.7979026558759832, 1.8178778282799497, 0.3802504844408982], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Percentile 1
            case 8:
            // Percentile 2
            case 9:
            // Percentile 3
            case 10:
            // Throughput
            case 11:
            // Kbytes/s
            case 12:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": []}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 980, 0, null, null, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
