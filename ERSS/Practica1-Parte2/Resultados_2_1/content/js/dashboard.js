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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.6443661971830986, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)  ", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "65 /wcal/js_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "61 /wcal/week.php"], "isController": false}, {"data": [1.0, 500, 1500, "39 /wcal/css_cacher.php"], "isController": false}, {"data": [1.0, 500, 1500, "64 /wcal/js_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "42 /wcal/month.php"], "isController": false}, {"data": [1.0, 500, 1500, "44 /wcal/css_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "41 /wcal/login.php"], "isController": false}, {"data": [1.0, 500, 1500, "43 /wcal/js_cacher.php"], "isController": false}, {"data": [1.0, 500, 1500, "38 /wcal/login.php"], "isController": false}, {"data": [1.0, 500, 1500, "63 /wcal/edit_entry.php"], "isController": false}, {"data": [1.0, 500, 1500, "68 /wcal/edit_entry_handler.php"], "isController": false}, {"data": [1.0, 500, 1500, "70 /wcal/login.php"], "isController": false}, {"data": [0.0, 500, 1500, "67 /wcal/edit_entry_handler.php"], "isController": false}, {"data": [0.0, 500, 1500, "71 /wcal/index.php"], "isController": false}, {"data": [1.0, 500, 1500, "73 /wcal/js_cacher.php"], "isController": false}, {"data": [0.0, 500, 1500, "69 /wcal/week.php"], "isController": false}, {"data": [1.0, 500, 1500, "62 /wcal/view_entry.php"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 852, 0, 0.0, 7273.339201877932, 8, 35771, 24139.0, 24936.7, 32759.480000000025, 1.3560357917052628, 11.124045070085723, 0.8075041068029387], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "90th pct", "95th pct", "99th pct", "Throughput", "Received", "Sent"], "items": [{"data": ["65 /wcal/js_cacher.php", 50, 0, 0.0, 16.919999999999995, 8, 70, 30.499999999999993, 55.0, 70.0, 0.10120453639213925, 0.23423315551696292, 0.0391376918078976], "isController": false}, {"data": ["61 /wcal/week.php", 50, 0, 0.0, 21475.339999999997, 19569, 23437, 22674.3, 23400.05, 23437.0, 0.09687068925432818, 0.8195222470803174, 0.04209712570134379], "isController": false}, {"data": ["39 /wcal/css_cacher.php", 53, 0, 0.0, 18.283018867924522, 9, 82, 37.00000000000001, 46.3, 82.0, 0.08775241236348705, 0.3716623168168392, 0.032564371775512774], "isController": false}, {"data": ["64 /wcal/js_cacher.php", 50, 0, 0.0, 16.5, 9, 58, 30.39999999999999, 39.94999999999995, 58.0, 0.10121252606222546, 0.4921261399060748, 0.04151295014270966], "isController": false}, {"data": ["42 /wcal/month.php", 50, 0, 0.0, 23794.54, 21009, 25642, 24933.2, 25174.3, 25642.0, 0.0966286271970934, 2.5521658702857306, 0.04076520209877378], "isController": false}, {"data": ["44 /wcal/css_cacher.php", 50, 0, 0.0, 23.019999999999996, 9, 67, 50.19999999999999, 57.04999999999996, 67.0, 0.10146598048606265, 0.42974409899223986, 0.03824791842541033], "isController": false}, {"data": ["41 /wcal/login.php", 53, 0, 0.0, 24017.471698113204, 16603, 27428, 26300.4, 26625.199999999997, 27428.0, 0.0843674984559156, 2.3982120557175697, 0.12539778579092142], "isController": false}, {"data": ["43 /wcal/js_cacher.php", 50, 0, 0.0, 23.680000000000003, 11, 85, 40.8, 60.0, 85.0, 0.10146392145880768, 0.2349325759558916, 0.03616633919186016], "isController": false}, {"data": ["38 /wcal/login.php", 53, 0, 0.0, 30.64150943396227, 15, 121, 56.000000000000014, 79.49999999999987, 121.0, 0.08774529362389717, 0.14722946304847018, 0.03701754574758163], "isController": false}, {"data": ["63 /wcal/edit_entry.php", 50, 0, 0.0, 29.520000000000003, 16, 80, 50.8, 71.04999999999995, 80.0, 0.10120515093736211, 0.8273323422818928, 0.04981191022698291], "isController": false}, {"data": ["68 /wcal/edit_entry_handler.php", 50, 0, 0.0, 39.720000000000006, 18, 187, 77.0, 116.9499999999997, 187.0, 0.10081925728469543, 0.2250711153836072, 0.11647380992948701], "isController": false}, {"data": ["70 /wcal/login.php", 50, 0, 0.0, 28.620000000000005, 17, 59, 43.9, 48.89999999999999, 59.0, 0.09833479852183132, 0.13444210735406625, 0.04407780519679743], "isController": false}, {"data": ["67 /wcal/edit_entry_handler.php", 50, 0, 0.0, 5162.839999999998, 4210, 7941, 6560.899999999999, 7508.749999999999, 7941.0, 0.09986358634105812, 0.2971526831847696, 0.11439451833795036], "isController": false}, {"data": ["71 /wcal/index.php", 50, 0, 0.0, 25544.339999999993, 20485, 35771, 34181.0, 34640.65, 35771.0, 0.09312322251049034, 2.5428823709358697, 0.08111905710874744], "isController": false}, {"data": ["73 /wcal/js_cacher.php", 43, 0, 0.0, 21.53488372093023, 11, 62, 33.6, 46.19999999999996, 62.0, 0.08749938953914274, 0.20259868417705806, 0.03135964449303261], "isController": false}, {"data": ["69 /wcal/week.php", 50, 0, 0.0, 22217.56, 19180, 33664, 29633.8, 30959.95, 33664.0, 0.09441729422694896, 0.7987666209844325, 0.04222961011322522], "isController": false}, {"data": ["62 /wcal/view_entry.php", 50, 0, 0.0, 36.20000000000001, 17, 107, 71.5, 86.79999999999998, 107.0, 0.10120658490524027, 0.23058101814836482, 0.04823126311890357], "isController": false}]}, function(index, item){
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
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 852, 0, null, null, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
