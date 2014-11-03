/*
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
var msg = function msg(data) {
    var resultArea = document.getElementById("AjaxGuess:result");
    var errorArea = document.getElementById("AjaxGuess:errors1"); 
    if (errorArea.innerHTML !== null && errorArea.innerHTML !== "") {
        resultArea.innerHTML="";
    }
};

