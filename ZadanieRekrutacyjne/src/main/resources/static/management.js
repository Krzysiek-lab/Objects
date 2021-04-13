$(function(){
    selectedStudentColumn = "id";
    selectedTeacherColumn = "id";
    currentStudentPage = 1;
    currentTeacherPage = 1;
    sortStudentAscending = true;
    sortTeacherAscending = true;

    $(document).on("click",".student-header", function() {
        if(selectedStudentColumn == $(this).text().trim().replace(/ /g,'')){
            sortStudentAscending = !sortStudentAscending;
        } else {
            sortStudentAscending = true;
        }

        selectedStudentColumn = $(this).text().trim().replace(/ /g,'');
    });
    $(document).on("click",".teacher-header", function() {
        if(selectedTeacherColumn == $(this).text().trim().replace(/ /g,'')){
            sortTeacherAscending = !sortTeacherAscending;
        } else {
            sortTeacherAscending = true;
        }

        selectedTeacherColumn = $(this).text().trim().replace(/ /g,'');
    });
    $(document).on("click",".student-pagination-btn", function() {
        currentStudentPage = $(this).text();
    });
    $(document).on("click",".teacher-pagination-btn", function() {
        currentTeacherPage = $(this).text();
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

function showStudents(element){
    var teacherId = $(element).attr("value");

    $.get( "http://localhost:8080/students/getForTeacher?teacherId="+teacherId, function( result )
    {
        var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];
        $(document).find(".students-container").html(resultBodyHtml);
    });
}