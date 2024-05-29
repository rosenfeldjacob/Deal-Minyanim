function replacePrayerNames() {
    // Check if 'Edot Hamizrach' is present on the page
    if (document.body.innerHTML.includes('Edot Hamizrach')) {
      // Get all the table elements on the page
      var tables = document.getElementsByTagName('table');
      
      // Loop through each table
      for (var i = 0; i < tables.length; i++) {
        // Replace 'Shacharis' with 'Shacharit' and 'Maariv' with 'Arvit' in that table
        tables[i].innerText = tables[i].innerText.replace(/Shacharis/g, 'Shacharit').replace(/Maariv/g, 'Arvit');
      }
    }
  }
  
  // Call the function to perform the replacement
  replacePrayerNames();
  