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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.6245330012453301, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)  ", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "65 /wcal/js_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "61 /wcal/week.php"], "isController": false}, {"data": [0.98125, 500, 1500, "39 /wcal/css_cacher.php"], "isController": false}, {"data": [1.0, 500, 1500, "64 /wcal/js_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "42 /wcal/month.php"], "isController": false}, {"data": [1.0, 500, 1500, "44 /wcal/css_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "41 /wcal/login.php"], "isController": false}, {"data": [1.0, 500, 1500, "43 /wcal/js_cacher.php"], "isController": false}, {"data": [0.7875, 500, 1500, "38 /wcal/login.php"], "isController": false}, {"data": [1.0, 500, 1500, "63 /wcal/edit_entry.php"], "isController": false}, {"data": [1.0, 500, 1500, "68 /wcal/edit_entry_handler.php"], "isController": false}, {"data": [1.0, 500, 1500, "70 /wcal/login.php"], "isController": false}, {"data": [0.0, 500, 1500, "67 /wcal/edit_entry_handler.php"], "isController": false}, {"data": [0.0, 500, 1500, "71 /wcal/index.php"], "isController": false}, {"data": [1.0, 500, 1500, "73 /wcal/js_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "69 /wcal/week.php"], "isController": false}, {"data": [1.0, 500, 1500, "62 /wcal/view_entry.php"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 803, 0, 0.0, 31128.853051058515, 8, 137198, 102269.6, 108672.59999999995, 133690.16000000003, 1.2566706834222756, 10.915356595467065, 0.7744399364622294], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "90th pct", "95th pct", "99th pct", "Throughput", "Received", "Sent"], "items": [{"data": ["65 /wcal/js_cacher.php", 40, 0, 0.0, 13.149999999999997, 8, 43, 18.9, 26.849999999999987, 43.0, 0.8088488059369502, 1.8720426465532929, 0.31279699917092996], "isController": false}, {"data": ["61 /wcal/week.php", 40, 0, 0.0, 96616.54999999999, 84742, 116908, 107497.09999999999, 110808.65, 116908.0, 0.2404684325065227, 2.034353545707037, 0.10450044186074472], "isController": false}, {"data": ["39 /wcal/css_cacher.php", 80, 0, 0.0, 94.27499999999999, 9, 1081, 158.9, 213.15000000000003, 1081.0, 0.1464144921064287, 0.6201204224881679, 0.05433350293012002], "isController": false}, {"data": ["64 /wcal/js_cacher.php", 40, 0, 0.0, 14.200000000000001, 9, 34, 19.799999999999997, 27.89999999999999, 34.0, 0.8088324503579084, 3.9327898147773688, 0.33174768471711086], "isController": false}, {"data": ["42 /wcal/month.php", 43, 0, 0.0, 117263.53488372092, 38280, 137198, 135637.2, 136060.6, 137198.0, 0.07549395872762833, 1.9939546950658553, 0.031849013838218206], "isController": false}, {"data": ["44 /wcal/css_cacher.php", 40, 0, 0.0, 58.425, 10, 220, 145.29999999999998, 182.99999999999991, 220.0, 0.5158494751231592, 2.1848038804776766, 0.19445107167728456], "isController": false}, {"data": ["41 /wcal/login.php", 80, 0, 0.0, 98159.2625, 69369, 106765, 104779.8, 105707.95, 106765.0, 0.12554809590619048, 3.5688318294044623, 0.18660566598556824], "isController": false}, {"data": ["43 /wcal/js_cacher.php", 40, 0, 0.0, 48.825, 16, 138, 92.5, 95.94999999999999, 138.0, 0.5159492821855612, 1.1946442852167631, 0.18390770312278304], "isController": false}, {"data": ["38 /wcal/login.php", 80, 0, 0.0, 464.8125000000002, 14, 2152, 1687.7000000000003, 1941.8500000000004, 2152.0, 0.1464115445502878, 0.2457111712008492, 0.061767370357152664], "isController": false}, {"data": ["63 /wcal/edit_entry.php", 40, 0, 0.0, 31.00000000000001, 17, 144, 51.699999999999996, 76.09999999999992, 144.0, 0.8087506823833883, 6.611378869366545, 0.39805697648557387], "isController": false}, {"data": ["68 /wcal/edit_entry_handler.php", 40, 0, 0.0, 40.47500000000001, 17, 129, 81.5, 111.49999999999996, 129.0, 0.833941415615553, 1.861709058688627, 0.963430365891796], "isController": false}, {"data": ["70 /wcal/login.php", 40, 0, 0.0, 27.575, 17, 44, 39.0, 42.79999999999998, 44.0, 0.7709357232340753, 1.0540136841090875, 0.34556591500433653], "isController": false}, {"data": ["67 /wcal/edit_entry_handler.php", 40, 0, 0.0, 20727.500000000004, 19397, 22439, 21816.0, 22237.6, 22439.0, 0.5733451824671043, 1.706037862282487, 0.656771385775306], "isController": false}, {"data": ["71 /wcal/index.php", 40, 0, 0.0, 98143.04999999997, 87780, 107059, 103226.4, 103673.65, 107059.0, 0.2577984016499098, 7.039608307553493, 0.2245665764372261], "isController": false}, {"data": ["73 /wcal/js_cacher.php", 40, 0, 0.0, 35.05, 12, 83, 60.9, 79.19999999999993, 83.0, 0.5934453956055369, 1.374081086895242, 0.21268990252659375], "isController": false}, {"data": ["69 /wcal/week.php", 40, 0, 0.0, 85623.37499999996, 82987, 89977, 87699.1, 87858.0, 89977.0, 0.2957180033120417, 2.5017627565353675, 0.13226449757511236], "isController": false}, {"data": ["62 /wcal/view_entry.php", 40, 0, 0.0, 37.55000000000002, 17, 143, 63.599999999999994, 95.09999999999992, 143.0, 0.8088488059369502, 1.842816664307524, 0.38546700907932785], "isController": false}]}, function(index, item){
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
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 803, 0, null, null, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
