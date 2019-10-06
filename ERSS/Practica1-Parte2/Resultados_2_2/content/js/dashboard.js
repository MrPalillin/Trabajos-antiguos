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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.6323170731707317, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)  ", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "65 /wcal/js_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "61 /wcal/week.php"], "isController": false}, {"data": [1.0, 500, 1500, "39 /wcal/css_cacher.php"], "isController": false}, {"data": [1.0, 500, 1500, "64 /wcal/js_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "42 /wcal/month.php"], "isController": false}, {"data": [1.0, 500, 1500, "44 /wcal/css_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "41 /wcal/login.php"], "isController": false}, {"data": [1.0, 500, 1500, "43 /wcal/js_cacher.php"], "isController": false}, {"data": [0.975, 500, 1500, "38 /wcal/login.php"], "isController": false}, {"data": [1.0, 500, 1500, "63 /wcal/edit_entry.php"], "isController": false}, {"data": [1.0, 500, 1500, "68 /wcal/edit_entry_handler.php"], "isController": false}, {"data": [1.0, 500, 1500, "70 /wcal/login.php"], "isController": false}, {"data": [0.0, 500, 1500, "67 /wcal/edit_entry_handler.php"], "isController": false}, {"data": [0.0, 500, 1500, "71 /wcal/index.php"], "isController": false}, {"data": [1.0, 500, 1500, "73 /wcal/js_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "69 /wcal/week.php"], "isController": false}, {"data": [1.0, 500, 1500, "62 /wcal/view_entry.php"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 820, 0, 0.0, 15249.541463414638, 8, 56010, 49130.9, 50586.299999999996, 52633.86, 1.2973882940767896, 11.13413572234071, 0.759137786592283], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "90th pct", "95th pct", "99th pct", "Throughput", "Received", "Sent"], "items": [{"data": ["65 /wcal/js_cacher.php", 40, 0, 0.0, 14.375000000000002, 8, 45, 24.799999999999997, 28.0, 45.0, 0.14896081213434775, 0.3447628171468791, 0.0576059390675798], "isController": false}, {"data": ["61 /wcal/week.php", 60, 0, 0.0, 42477.03333333334, 34386, 46874, 45138.6, 45711.3, 46874.0, 0.109210643669332, 0.9239177794017804, 0.04745970354770775], "isController": false}, {"data": ["39 /wcal/css_cacher.php", 60, 0, 0.0, 41.51666666666667, 9, 155, 84.8, 107.24999999999994, 155.0, 0.12034056379554138, 0.5096845949035771, 0.04465763109600169], "isController": false}, {"data": ["64 /wcal/js_cacher.php", 40, 0, 0.0, 15.150000000000002, 10, 30, 25.799999999999997, 27.949999999999996, 30.0, 0.14895970267643344, 0.7242874605722287, 0.061096753050880914], "isController": false}, {"data": ["42 /wcal/month.php", 60, 0, 0.0, 47967.95000000001, 43779, 52553, 50788.9, 51217.65, 52553.0, 0.10740254114412348, 2.836727663851527, 0.045310447045177095], "isController": false}, {"data": ["44 /wcal/css_cacher.php", 60, 0, 0.0, 25.73333333333333, 10, 74, 52.599999999999994, 68.64999999999998, 74.0, 0.11652657582772712, 0.4935310150047387, 0.0439250569038112], "isController": false}, {"data": ["41 /wcal/login.php", 60, 0, 0.0, 50272.916666666664, 38750, 56010, 52720.3, 55442.14999999999, 56010.0, 0.10935275924349762, 3.1084393931696446, 0.16253408160996421], "isController": false}, {"data": ["43 /wcal/js_cacher.php", 60, 0, 0.0, 26.683333333333337, 12, 95, 50.599999999999994, 66.79999999999998, 95.0, 0.11653110215116413, 0.2698195734379006, 0.04153696512224112], "isController": false}, {"data": ["38 /wcal/login.php", 60, 0, 0.0, 105.56666666666666, 15, 992, 260.5999999999999, 516.2499999999993, 992.0, 0.12033887426994416, 0.2019358388221231, 0.05076796258263269], "isController": false}, {"data": ["63 /wcal/edit_entry.php", 40, 0, 0.0, 25.249999999999996, 15, 67, 35.699999999999996, 59.04999999999992, 67.0, 0.14895581970387584, 1.217684733145649, 0.07331419251050139], "isController": false}, {"data": ["68 /wcal/edit_entry_handler.php", 40, 0, 0.0, 33.0, 17, 68, 52.9, 60.79999999999998, 68.0, 0.14961045178616178, 0.3339936452960604, 0.17284098092092715], "isController": false}, {"data": ["70 /wcal/login.php", 40, 0, 0.0, 28.625000000000007, 18, 59, 46.599999999999994, 52.74999999999998, 59.0, 0.14914076278043123, 0.20390338661387086, 0.06685118175411908], "isController": false}, {"data": ["67 /wcal/edit_entry_handler.php", 40, 0, 0.0, 10185.95, 9067, 11184, 10926.5, 10981.45, 11184.0, 0.1437344362555742, 0.4276941672565768, 0.1646489196560435], "isController": false}, {"data": ["71 /wcal/index.php", 40, 0, 0.0, 48480.32500000001, 43910, 52188, 51224.2, 52062.65, 52188.0, 0.12530307681705122, 3.421606087849987, 0.10915072707110324], "isController": false}, {"data": ["73 /wcal/js_cacher.php", 40, 0, 0.0, 28.199999999999996, 12, 54, 40.9, 52.54999999999996, 54.0, 0.14527229475748604, 0.3363677840527338, 0.05206536345312244], "isController": false}, {"data": ["69 /wcal/week.php", 40, 0, 0.0, 42400.625000000015, 39777, 44739, 43411.5, 44474.25, 44739.0, 0.12912681221410516, 1.0924077873152276, 0.057753984369199374], "isController": false}, {"data": ["62 /wcal/view_entry.php", 40, 0, 0.0, 28.0, 16, 54, 37.9, 53.49999999999996, 54.0, 0.14894860900617765, 0.339352641417395, 0.07098332147950653], "isController": false}]}, function(index, item){
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
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 820, 0, null, null, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
