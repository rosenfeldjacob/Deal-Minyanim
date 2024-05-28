// Function to replace 'Shacharis' with 'Shacharit' if 'Edot Hamizrach' is present
function replaceShacharis() {
  // Get the entire HTML document
  var doc = document.documentElement;

  // Check if 'Edot Hamizrach' is in the document
  if (doc.innerHTML.includes('Edot Hamizrach')) {
    // Replace 'Shacharis' with 'Shacharit'
    doc.innerHTML = doc.innerHTML.replace(/Shacharis/g, 'Shacharit');
    doc.innerHTML = doc.innerHTML.replace(/Maariv/g, 'Arvit');
  }
}

// Call the function to perform the replacement
replaceShacharis();