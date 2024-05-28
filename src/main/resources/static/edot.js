// Function to replace 'Shacharis' with 'Shacharit' and 'Maariv' with 'Arvit' if 'Edot Hamizrach' is present
function replacePrayerNames() {
    // Get all text nodes in the document
    var walker = document.createTreeWalker(
      document.body,
      NodeFilter.SHOW_TEXT,
      null,
      false
    );
  
    // Define a function to check and replace text
    function checkAndReplace(node) {
      if (node.textContent.includes('Edot Hamizrach')) {
        node.textContent = node.textContent.replace(/Shacharis/g, 'Shacharit').replace(/Maariv/g, 'Arvit');
      }
    }
  
    // Walk through the text nodes and replace as needed
    var node;
    while (node = walker.nextNode()) {
      checkAndReplace(node);
    }
  }
  
  // Call the function to perform the replacement
  replacePrayerNames();
  