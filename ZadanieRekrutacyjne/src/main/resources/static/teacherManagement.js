function getPaginatedPageForTeachers() {
    setTimeout(
        function() {
            $.get( "http://localhost:8080/teachers?page="+currentTeacherPage+"&column=" + selectedTeacherColumn+"&sortAscending="+sortTeacherAscending, function( result )
                {
                    var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];
                    $(document).find(".teachers-container").html(resultBodyHtml);
                });
        },
    100);
}

function getFilteredForTeachers() {
    var filterValue = $(".teacher-filter-input").val();

    $.get( "http://localhost:8080/teachers/getFiltered?filterValue="+filterValue, function( result )
    {
        var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];
        $(document).find(".teachers-container").html(resultBodyHtml);
    });
}

function deleteTeacher(element) {
    var teacherId = $(element).attr("value");
    $.get( "http://localhost:8080/teachers/delete?id="+teacherId, getPaginatedPageForTeachers);
}

function addStudentToTeacher(element) {
    var studentId = $(element).attr("value");
    var teacherId = $("#teacherId").val();
    $.get( "http://localhost:8080/teachers/addStudentToTeacher?teacherId="+teacherId+"&studentId="+studentId, function(result){
        var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];
        $(".available-students").html(resultBodyHtml);
    });
}
function removeStudentOfTeacher(element) {
    var studentId = $(element).attr("value");
    var teacherId = $("#teacherId").val();
    $.get( "http://localhost:8080/teachers/removeStudentOfTeacher?teacherId="+teacherId+"&studentId="+studentId, function(result){
        var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];
        $(".available-students").html(resultBodyHtml);
    });
}