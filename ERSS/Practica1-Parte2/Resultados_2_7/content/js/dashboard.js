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

    var data = {"OkPercent": 98.42180165254148, "KoPercent": 1.578198347458527};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.2400327109751694, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)  ", "F (Frustration threshold)", "Label"], "items": [{"data": [0.23782703488372092, 500, 1500, "65 /wcal/js_cacher.php"], "isController": false}, {"data": [0.2537339971550498, 500, 1500, "61 /wcal/week.php"], "isController": false}, {"data": [0.2857142857142857, 500, 1500, "39 /wcal/css_cacher.php"], "isController": false}, {"data": [0.2356342609324178, 500, 1500, "64 /wcal/js_cacher.php"], "isController": false}, {"data": [0.21542461005199307, 500, 1500, "42 /wcal/month.php"], "isController": false}, {"data": [0.24618659098971266, 500, 1500, "44 /wcal/css_cacher.php"], "isController": false}, {"data": [0.22996931469485168, 500, 1500, "41 /wcal/login.php"], "isController": false}, {"data": [0.25449101796407186, 500, 1500, "43 /wcal/js_cacher.php"], "isController": false}, {"data": [0.2924655230406996, 500, 1500, "38 /wcal/login.php"], "isController": false}, {"data": [0.24216780698595608, 500, 1500, "63 /wcal/edit_entry.php"], "isController": false}, {"data": [0.21610965076980848, 500, 1500, "68 /wcal/edit_entry_handler.php"], "isController": false}, {"data": [0.20948540706605223, 500, 1500, "70 /wcal/login.php"], "isController": false}, {"data": [0.22654253377144942, 500, 1500, "67 /wcal/edit_entry_handler.php"], "isController": false}, {"data": [0.20852623456790123, 500, 1500, "71 /wcal/index.php"], "isController": false}, {"data": [0.24695241840346047, 500, 1500, "73 /wcal/js_cacher.php"], "isController": false}, {"data": [0.24254998113919277, 500, 1500, "69 /wcal/week.php"], "isController": false}, {"data": [0.22673374056773266, 500, 1500, "62 /wcal/view_entry.php"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 47079, 743, 1.578198347458527, 6532.646232927683, 9, 175069, 5220.9000000000015, 23274.15000000036, 120091.0, 55.47895808586459, 242.31073377466734, 50.105062129754344], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "90th pct", "95th pct", "99th pct", "Throughput", "Received", "Sent"], "items": [{"data": ["65 /wcal/js_cacher.php", 2752, 0, 0.0, 3529.9407703488373, 9, 82789, 4262.0, 6904.449999999999, 58041.90999999963, 4.155900638939521, 17.472996367362285, 2.9927549462692786], "isController": false}, {"data": ["61 /wcal/week.php", 2812, 140, 4.978662873399715, 9966.36557610243, 11, 120102, 11751.40000000003, 119754.74999999997, 120099.87, 3.607468434651197, 15.340090865436423, 2.9691537074050602], "isController": false}, {"data": ["39 /wcal/css_cacher.php", 2947, 0, 0.0, 3259.7590770274855, 9, 82884, 4646.200000000002, 6471.5999999999885, 57925.27999999998, 4.087270931833975, 18.436376210699716, 2.7658487393362727], "isController": false}, {"data": ["64 /wcal/js_cacher.php", 2767, 0, 0.0, 4125.593783881467, 12, 83805, 4366.200000000004, 9191.4, 73846.92000000033, 4.179076525279863, 19.363646407100504, 3.121661349998792], "isController": false}, {"data": ["42 /wcal/month.php", 2885, 112, 3.882149046793761, 11301.22426343157, 11, 120102, 39063.20000000002, 61871.09999999999, 120099.0, 3.6999702463397868, 15.730370449501498, 3.0278477266664616], "isController": false}, {"data": ["44 /wcal/css_cacher.php", 2819, 0, 0.0, 3612.631074849241, 9, 84169, 4510.0, 10119.0, 56088.60000000002, 4.2550429579718285, 19.397273139060207, 3.130721334214077], "isController": false}, {"data": ["41 /wcal/login.php", 2933, 182, 6.205250596658711, 15007.697579270372, 10, 175069, 47258.6, 120262.6, 120830.2, 3.4574217276499435, 15.730239022052409, 3.6756172309682666], "isController": false}, {"data": ["43 /wcal/js_cacher.php", 2839, 0, 0.0, 4076.748150757305, 10, 82447, 5670.0, 12080.0, 63787.0, 4.285173059710166, 19.090261905543553, 3.031403639925466], "isController": false}, {"data": ["38 /wcal/login.php", 2973, 0, 0.0, 4709.345442314165, 10, 89118, 5865.799999999999, 12615.399999999983, 71619.03999999918, 4.121867526255589, 17.36507048065925, 3.307164396381408], "isController": false}, {"data": ["63 /wcal/edit_entry.php", 2777, 0, 0.0, 3779.1134317608894, 10, 82759, 4453.000000000002, 7612.899999999998, 62855.11999999994, 4.191312196160052, 19.441651822907627, 3.8884730401238525], "isController": false}, {"data": ["68 /wcal/edit_entry_handler.php", 2663, 0, 0.0, 4690.878332707467, 10, 82674, 6194.199999999999, 12853.599999999966, 75206.32, 4.022737606629737, 17.289168938551665, 6.548217655237187], "isController": false}, {"data": ["70 /wcal/login.php", 2604, 0, 0.0, 5922.213133640545, 10, 85309, 10202.5, 27328.75, 79039.4, 3.932643660801933, 17.623517495091747, 3.478055033791437], "isController": false}, {"data": ["67 /wcal/edit_entry_handler.php", 2739, 148, 5.403431909456006, 11635.610076670317, 9, 120102, 30516.0, 120025.0, 120100.0, 3.6127177160343202, 14.92783313191234, 5.559076444213255], "isController": false}, {"data": ["71 /wcal/index.php", 2592, 57, 2.199074074074074, 7182.600694444451, 10, 126773, 6478.500000000002, 46039.54999999989, 123033.29000000001, 3.3125489629753644, 14.090176960819395, 2.920570591990328], "isController": false}, {"data": ["73 /wcal/js_cacher.php", 2543, 0, 0.0, 4490.981124655916, 11, 83089, 5101.5999999999985, 11216.399999999998, 77792.2799999999, 3.840507919631746, 16.736416650274407, 2.6632046555209374], "isController": false}, {"data": ["69 /wcal/week.php", 2651, 104, 3.923047906450396, 9521.388155413055, 11, 120102, 13424.000000000106, 61773.00000000004, 120098.0, 3.400243699095748, 14.37072499478933, 2.8924061477906755], "isController": false}, {"data": ["62 /wcal/view_entry.php", 2783, 0, 0.0, 3899.161696011504, 10, 83370, 4781.199999999997, 10627.599999999977, 61698.43999999996, 4.205147112153374, 17.713503730028197, 3.75023798443053], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 6, 0.8075370121130552, 0.012744535780284203], "isController": false}, {"data": ["Non HTTP response code: java.net.SocketTimeoutException/Non HTTP response message: Read timed out", 737, 99.19246298788694, 1.565453811678243], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 47079, 743, "Non HTTP response code: java.net.SocketTimeoutException/Non HTTP response message: Read timed out", 737, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 6, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["61 /wcal/week.php", 2812, 140, "Non HTTP response code: java.net.SocketTimeoutException/Non HTTP response message: Read timed out", 140, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["42 /wcal/month.php", 2885, 112, "Non HTTP response code: java.net.SocketTimeoutException/Non HTTP response message: Read timed out", 106, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 6, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["41 /wcal/login.php", 2933, 182, "Non HTTP response code: java.net.SocketTimeoutException/Non HTTP response message: Read timed out", 182, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["67 /wcal/edit_entry_handler.php", 2739, 148, "Non HTTP response code: java.net.SocketTimeoutException/Non HTTP response message: Read timed out", 148, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["71 /wcal/index.php", 2592, 57, "Non HTTP response code: java.net.SocketTimeoutException/Non HTTP response message: Read timed out", 57, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}, {"data": ["69 /wcal/week.php", 2651, 104, "Non HTTP response code: java.net.SocketTimeoutException/Non HTTP response message: Read timed out", 104, null, null, null, null, null, null, null, null], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
