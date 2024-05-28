// Function to replace 'Shacharis' with 'Shacharit' and 'Maariv' with 'Arvit' if 'Edot Hamizrach' is present
function replacePrayerNames() {
    // Get all elements with class 'org-nusach'
    var orgNusachElements = document.getElementsByClassName('org-nusach');
  
    // Check if any of these elements contain 'Edot Hamizrach'
    var containsEdotHamizrach = Array.from(orgNusachElements).some(el => el.innerHTML.includes('Edot Hamizrach'));
  
    if (containsEdotHamizrach) {
      // Get all the table cell elements on the page
      var cells = document.getElementsByTagName('td');
  
      // Loop through each cell
      for (var i = 0; i < cells.length; i++) {
        // Replace 'Shacharis' with 'Shacharit' and 'Maariv' with 'Arvit' in that cell
        cells[i].innerHTML = cells[i].innerHTML.replace(/Shacharis/g, 'Shacharit').replace(/Maariv/g, 'Arvit');
      }
  
      // Get all the button elements on the page
      var buttons = document.getElementsByTagName('button');
  
      // Loop through each button
      for (var i = 0; i < buttons.length; i++) {
        // Replace 'Shacharis' with 'Shacharit' and 'Maariv' with 'Arvit' in that button
        buttons[i].innerHTML = buttons[i].innerHTML.replace(/Shacharis/g, 'Shacharit').replace(/Maariv/g, 'Arvit');
      }
    }
  }
  
  // Call the function to perform the replacement
  replacePrayerNames();