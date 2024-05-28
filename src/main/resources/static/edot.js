// Function to replace 'Shacharis' with 'Shacharit' if 'Edot Hamizrach' is present
function replaceShacharis() {
    // Get all the table elements on the page
    var tables = document.getElementsByClassName('org-nusach');
  
    // Loop through each table
    for (var i = 0; i < tables.length; i++) {
      // Check if 'Edot Hamizrach' is in the table
      if (tables[i].innerHTML.includes('Edot Hamizrach')) {
        // Replace 'Shacharis' with 'Shacharit'
        tables[i].innerHTML = tables[i].innerHTML.replace(/Shacharis/g, 'Shacharit');
      }
    }
  }
  
  // Call the function to perform the replacement
  replaceShacharis();