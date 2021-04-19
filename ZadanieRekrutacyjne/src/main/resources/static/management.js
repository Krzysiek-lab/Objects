$(function(){// wykonuje sie automatyczenie po wejsciu na strone z management.js
    selectedStudentColumn = "id";// zmienne globalne czyli widziane  dla kazdego .js w apce
    selectedTeacherColumn = "id";
    currentStudentPage = 1;
    currentTeacherPage = 1;
    sortStudentAscending = true;
    sortTeacherAscending = true;

    $(document).on("click",".student-header", function() {                  //    po kliknieciu na element z klasa .student-header wkonuje sie ponizsza funkcja, ktora sprawdza czy selectedStudentColumn
                                                                            //    z wartoscia początkowa wynosząca "id" rowna sie nazwie kliknietej kolumny z ktore
        if(selectedStudentColumn == $(this).text().trim().replace(/ /g,'')){// usuwa spacje z poczotku srodka i konca nazwy kolumny na ktora kliknieto ($(this))
            sortStudentAscending = !sortStudentAscending;                   // przypisanie wartosci true albo false do sortStudentAscending w zaleznosci od tego czy juz byla klikana na nazwa kolumny czy nie
        } else {
            sortStudentAscending = true;
        }

        selectedStudentColumn = $(this).text().trim().replace(/ /g,'');     // metoda ze studentManagement.js getPaginatedPageForStudents(), do kturej przekazane jest selectedStudentColumn i sortStudentAscendin
                                                                            //a ona przekazuje je do backendu
                                                                            // ustawianie selectedColumnName na nazwe kliknietej kolumny by moc sortowac za kazdym kliknieciem
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
        currentStudentPage = $(this).text();// metoda ze studentManagement.js getPaginatedPageForStudents(),
        // przypisywanie numerku strony (html students ostatnia linijka)
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
    // zwraca nayczycieli ucznia, pobiera jako parametr element taga z ta metoda czyli Teacher w templatce students
    // i pobiera z niego atrybut value zapisuje do studentId i wtlacza d metody getForTeacher z contrllera i podminia
    // tag z klasa teachers-container na wynik tej metody z controlera
    var studentId = $(element).attr("value");

    $.get( "http://localhost:8080/teachers/getForStudent?studentId="+studentId, function( result )
    {
        var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];// zwraca body html'ki do var'a resultBdyHtml
        $(document).find(".teachers-container").html(resultBodyHtml);// szuka wszedzie students-container i zamienia jego wnetrze (.html)
        // resultBodyHtml
    });
}
//.get -> wywoluje controller ktory zwraca templatke getForStudents, pozniej w function(result) jest wynik tego get'a

function showStudents(element){
    var teacherId = $(element).attr("value");

    $.get( "http://localhost:8080/students/getForTeacher?teacherId="+teacherId, function( result )
    {
        var resultBodyHtml = /<body.*?>([\s\S]*)<\/body>/.exec(result)[1];
        $(document).find(".students-container").html(resultBodyHtml);
    });
}