$(function(){
    selectedColumn = "id";
    currentPage = 1;
    sortAscending = true;

    $(document).on("click","th", function() {
        if(selectedColumn == $(this).text().trim().replace(/ /g,'')){
            sortAscending = !sortAscending;
        } else {
            sortAscending = true;
        }

        selectedColumn = $(this).text().trim().replace(/ /g,'');
    });
    $(document).on("click",".pagination-btn", function() {
        currentPage = $(this).text();
    });

    $.get( "http://localhost:8080/students", function( result )
    {
        var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];
        $(document).find(".students-container").html(resultBodyHtml);
    });

    $.get( "http://localhost:8080/teachers", function( result )
    {
        var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];
        $(document).find(".teachers-container").html(resultBodyHtml);
    });
})

function showTeachers(element){
    var studentId = $(element).attr("value");

    $.get( "http://localhost:8080/teachers/getForStudent?studentId="+studentId, function( result )
    {
        var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];
        $(document).find(".teachers-container").html(resultBodyHtml);
    });
}