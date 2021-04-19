function getPaginatedPageForStudents() {
    setTimeout(
        function() {
            $.get( "http://localhost:8080/students?page="+currentStudentPage+"&column=" + selectedStudentColumn+"&sortAscending="+sortStudentAscending, function( result )
                {
                    var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];
                    $(document).find(".students-container").html(resultBodyHtml);
                });
        },
    100);
}

function getFilteredForStudents() {
// w templatce students jest input i z niego zczytuje wpisana do niego wartosc i tu podaje do metody getFiltered
// zwracającej danego studenta i zamieniam cialo diva z klasa student-container na znalezionych studentow
    var filterValue = $(".student-filter-input").val();

    $.get( "http://localhost:8080/students/getFiltered?filterValue="+filterValue, function( result )
    {
        var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];
        $(document).find(".students-container").html(resultBodyHtml);
    });
}

function deleteStudent(element) {
    var studentId = $(element).attr("value");
    $.get( "http://localhost:8080/students/delete?id="+studentId, getPaginatedPageForStudents);
}

function addTeacherToStudent(element) {// jak nauczyciel nie jest przypisany do ucznia wykoa sie ta metoda gdzie parametrme bedize id nauczyciela
//
    var teacherId = $(element).attr("value");
    var studentId = $("#studentId").val();
    $.get( "http://localhost:8080/students/addTeacherToStudent?teacherId="+teacherId+"&studentId="+studentId, function(result){
        var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];
        $(".available-teachers").html(resultBodyHtml);
    });
}
function removeTeacherOfStudent(element) {
    var teacherId = $(element).attr("value");
    var studentId = $("#studentId").val();
    $.get( "http://localhost:8080/students/removeTeacherOfStudent?teacherId="+teacherId+"&studentId="+studentId, function(result){
        var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];
        $(".available-teachers").html(resultBodyHtml);
    });
}