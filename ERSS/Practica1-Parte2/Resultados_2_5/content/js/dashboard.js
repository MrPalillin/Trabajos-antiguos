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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.40348472983555206, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)  ", "F (Frustration threshold)", "Label"], "items": [{"data": [0.7076923076923077, 500, 1500, "65 /wcal/js_cacher.php"], "isController": false}, {"data": [0.4963768115942029, 500, 1500, "61 /wcal/week.php"], "isController": false}, {"data": [0.7290909090909091, 500, 1500, "39 /wcal/css_cacher.php"], "isController": false}, {"data": [0.6, 500, 1500, "64 /wcal/js_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "42 /wcal/month.php"], "isController": false}, {"data": [0.4384057971014493, 500, 1500, "44 /wcal/css_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "41 /wcal/login.php"], "isController": false}, {"data": [0.09782608695652174, 500, 1500, "43 /wcal/js_cacher.php"], "isController": false}, {"data": [0.37636363636363634, 500, 1500, "38 /wcal/login.php"], "isController": false}, {"data": [0.55, 500, 1500, "63 /wcal/edit_entry.php"], "isController": false}, {"data": [0.5040983606557377, 500, 1500, "68 /wcal/edit_entry_handler.php"], "isController": false}, {"data": [0.7808219178082192, 500, 1500, "70 /wcal/login.php"], "isController": false}, {"data": [0.6076923076923076, 500, 1500, "67 /wcal/edit_entry_handler.php"], "isController": false}, {"data": [0.19863013698630136, 500, 1500, "71 /wcal/index.php"], "isController": false}, {"data": [0.8, 500, 1500, "73 /wcal/js_cacher.php"], "isController": false}, {"data": [0.430327868852459, 500, 1500, "69 /wcal/week.php"], "isController": false}, {"data": [0.5076923076923077, 500, 1500, "62 /wcal/view_entry.php"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 2554, 0, 0.0, 111965.60963194983, 9, 1434621, 320856.5, 1393865.75, 1405317.4, 0.0018539932560044361, 0.01148777250746379, 2.616904176305571E-4], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "90th pct", "95th pct", "99th pct", "Throughput", "Received", "Sent"], "items": [{"data": ["65 /wcal/js_cacher.php", 130, 0, 0.0, 571.1846153846154, 14, 1283, 839.8, 922.6499999999999, 1209.5299999999995, 31.167585710860706, 142.47861911112443, 0.0], "isController": false}, {"data": ["61 /wcal/week.php", 138, 0, 0.0, 7953.586956521739, 14, 166334, 1333.3000000000002, 107082.24999999991, 158096.4199999997, 1.0018569323863955E-4, 4.817608059633527E-4, 2.523926261956216E-6], "isController": false}, {"data": ["39 /wcal/css_cacher.php", 275, 0, 0.0, 652.0799999999999, 9, 3238, 1724.4000000000003, 2216.3999999999996, 2961.84, 1.9972802098476515E-4, 8.460510176417931E-4, 3.5037515044912633E-5], "isController": false}, {"data": ["64 /wcal/js_cacher.php", 130, 0, 0.0, 619.7076923076924, 17, 1147, 853.9000000000001, 948.8499999999997, 1142.04, 32.961460446247465, 151.2557642938641, 0.0], "isController": false}, {"data": ["42 /wcal/month.php", 260, 0, 0.0, 164741.75000000006, 7159, 342939, 318517.5, 323015.25, 336543.5499999999, 1.8875311144349517E-4, 0.002925304568953387, 3.9815109445112265E-5], "isController": false}, {"data": ["44 /wcal/css_cacher.php", 138, 0, 0.0, 1137.9492753623197, 10, 2997, 1915.8000000000006, 2445.7, 2961.509999999999, 1.0019329428280328E-4, 4.5725952909652653E-4, 2.189459442547671E-6], "isController": false}, {"data": ["41 /wcal/login.php", 275, 0, 0.0, 287319.16727272724, 81431, 1403964, 387184.8, 1394533.4, 1403590.32, 1.996725012295415E-4, 0.002949855598961824, 1.4029545842783055E-4], "isController": false}, {"data": ["43 /wcal/js_cacher.php", 138, 0, 0.0, 2525.9057971014486, 12, 6601, 4188.400000000001, 4572.949999999991, 6600.22, 1.0019281504646732E-4, 4.4656676179826826E-4, 2.0703338706952317E-6], "isController": false}, {"data": ["38 /wcal/login.php", 275, 0, 0.0, 2267.1454545454562, 17, 8132, 5370.0, 6739.4, 8090.72, 1.9972835955353195E-4, 3.3527128719599707E-4, 3.98321898882328E-5], "isController": false}, {"data": ["63 /wcal/edit_entry.php", 130, 0, 0.0, 714.1615384615385, 11, 1640, 1015.9, 1119.6499999999999, 1500.189999999999, 38.93381251871818, 178.47198824498352, 0.0], "isController": false}, {"data": ["68 /wcal/edit_entry_handler.php", 122, 0, 0.0, 1039.0983606557377, 63, 3381, 2808.2000000000003, 2942.8999999999996, 3343.7399999999993, 21.638879035118837, 89.16961355977297, 0.0], "isController": false}, {"data": ["70 /wcal/login.php", 73, 0, 0.0, 543.8219178082193, 19, 1474, 978.6000000000009, 1351.2999999999997, 1474.0, 23.18932655654384, 62.83565458227446, 0.0], "isController": false}, {"data": ["67 /wcal/edit_entry_handler.php", 130, 0, 0.0, 87011.8846153846, 17, 1428044, 926.8, 1397131.35, 1422329.46, 0.09088138162070865, 0.39373921657452693, 0.0], "isController": false}, {"data": ["71 /wcal/index.php", 73, 0, 0.0, 1113281.5753424654, 119, 1434621, 1406728.2, 1416366.7, 1434621.0, 0.05084574584788764, 0.10612226529986801, 0.0], "isController": false}, {"data": ["73 /wcal/js_cacher.php", 15, 0, 0.0, 486.4, 121, 1923, 1343.4000000000003, 1923.0, 1923.0, 5.971337579617835, 14.725800532444268, 0.0], "isController": false}, {"data": ["69 /wcal/week.php", 122, 0, 0.0, 562345.631147541, 17, 1434619, 1401617.3, 1406807.9, 1428923.74, 0.0849203106412806, 0.25211125074044244, 0.0], "isController": false}, {"data": ["62 /wcal/view_entry.php", 130, 0, 0.0, 768.1384615384617, 20, 1458, 1089.6, 1171.6499999999999, 1439.09, 36.39417693169093, 166.83033839585667, 0.0], "isController": false}]}, function(index, item){
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
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 2554, 0, null, null, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
