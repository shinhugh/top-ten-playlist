// DOM elements

const topBarHomeLink = document.getElementById('top-bar-home-link');
const topBarSearchInput = document.getElementById('top-bar-search-input');
const topBarSearchButton = document.getElementById('top-bar-search-button');
const topBarLoginLink = document.getElementById('top-bar-login-link');
const topBarSignUpLink = document.getElementById('top-bar-sign-up-link');
const topBarAccountLink = document.getElementById('top-bar-account-link');
const topBarLogoutLink = document.getElementById('top-bar-logout-link');

const homePageRoot = document.getElementById('home-page-root');
const homeUnauthenticatedRoot = document.getElementById('home-unauthenticated-root');
const homeAuthenticatedRoot = document.getElementById('home-authenticated-root');
const homePlaylistEditorTitleInput = document.getElementById('home-playlist-editor-title-input');
const homePlaylistEditorEntriesContainer = document.getElementById('home-playlist-editor-entries-container');
const homePlaylistEditorAddButton = document.getElementById('home-playlist-editor-add-button');
const homePlaylistEditorSubmitButton = document.getElementById('home-playlist-editor-submit-button');
const homeLoadingOverlay = document.getElementById('home-loading-overlay');

const loginPageRoot = document.getElementById('login-page-root');
const loginNameInput = document.getElementById('login-name-input');
const loginPasswordInput = document.getElementById('login-password-input');
const loginButton = document.getElementById('login-button');
const loginLoadingOverlay = document.getElementById('login-loading-overlay');

const signUpPageRoot = document.getElementById('sign-up-page-root');
const signUpLoginNameInput = document.getElementById('sign-up-login-name-input');
const signUpPasswordInput = document.getElementById('sign-up-password-input');
const signUpPublicNameInput = document.getElementById('sign-up-public-name-input');
const signUpButton = document.getElementById('sign-up-button');
const signUpLoadingOverlay = document.getElementById('sign-up-loading-overlay');

const accountPageRoot = document.getElementById('account-page-root');
const accountLoginNameInput = document.getElementById('account-login-name-input');
const accountPasswordInput = document.getElementById('account-password-input');
const accountPublicNameInput = document.getElementById('account-public-name-input');
const accountUpdateButton = document.getElementById('account-update-button');
const accountDeleteButton = document.getElementById('account-delete-button');
const accountLoadingOverlay = document.getElementById('account-loading-overlay');

const playlistPageRoot = document.getElementById('playlist-page-root');
const playlistTitle = document.getElementById('playlist-title');
const playlistUserPublicName = document.getElementById('playlist-user-public-name');
const playlistList = document.getElementById('playlist-list');

const systemMessageDialog = document.getElementById('system-message-dialog');
const systemMessageDialogContent = document.getElementById('system-message-dialog-content');

// ------------------------------------------------------------

// Functions: Utility

const extractInput = (element) => {
  const inputValue = element.value;
  return inputValue.length == 0 ? null : inputValue;
};

// ------------------------------------------------------------

// Functions: Global

const clearPageContents = () => {
  homeUnauthenticatedRoot.hidden = true;
  homeAuthenticatedRoot.hidden = true;
  homePlaylistEditorTitleInput.value = '';
  homePlaylistEditorEntriesContainer.innerHTML = '';
  loginNameInput.value = '';
  loginPasswordInput.value = '';
  signUpLoginNameInput.value = '';
  signUpPasswordInput.value = '';
  signUpPublicNameInput.value = '';
  accountLoginNameInput.value = '';
  accountPasswordInput.value = '';
  accountPublicNameInput.value = '';
  playlistTitle.innerHTML = '';
  playlistUserPublicName.innerHTML = '';
  playlistList.innerHTML = '';
};

const navigateToHomePage = () => {
  clearPageContents();
  loginPageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  accountPageRoot.hidden = true;
  homePageRoot.hidden = false;
  homeLoadingOverlay.hidden = false;
  fetch('http://localhost:8080/api/user/session')
  .then((response) => {
    if (response.ok) {
      updateTopBar(true);
      homeAuthenticatedRoot.hidden = false;
      populatePlaylistEditor()
      .finally(() => {
        homeLoadingOverlay.hidden = true;
      });
    } else {
      updateTopBar(false);
      homeUnauthenticatedRoot.hidden = false;
      homeLoadingOverlay.hidden = true;
    }
  })
  .catch(() => {
    updateTopBar(false);
    homeUnauthenticatedRoot.hidden = false;
    homeLoadingOverlay.hidden = true;
  });
};

const navigateToLoginPage = () => {
  clearPageContents();
  homePageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  accountPageRoot.hidden = true;
  loginPageRoot.hidden = false;
};

const navigateToSignUpPage = () => {
  clearPageContents();
  homePageRoot.hidden = true;
  loginPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  accountPageRoot.hidden = true;
  signUpPageRoot.hidden = false;
};

const navigateToAccountPage = () => {
  clearPageContents();
  homePageRoot.hidden = true;
  loginPageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  accountPageRoot.hidden = false;
  accountUpdateButton.disabled = true;
  accountLoadingOverlay.hidden = false;
  fetch('http://localhost:8080/api/user/session')
  .then((response) => {
    if (response.ok) {
      response.json()
      .then((data) => {
        accountLoginNameInput.value = data.loginName;
        accountPublicNameInput.value = data.publicName;
      });
    } else {
      showSystemMessage('Unable to fetch account data');
    }
  })
  .catch(() =>  {
    showSystemMessage('Unable to fetch account data');
  })
  .finally(() => {
    accountUpdateButton.disabled = false;
    accountLoadingOverlay.hidden = true;
  });
};

const navigateToPlaylistPage = (user, songList) => {
  clearPageContents();
  homePageRoot.hidden = true;
  loginPageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  accountPageRoot.hidden = true;
  playlistPageRoot.hidden = false;
  playlistTitle.innerHTML = songList.title;
  playlistUserPublicName.innerHTML = user.publicName;
  songList.entries.forEach((songListEntry) => {
    const songListEntryIFrame = document.createElement('iframe');
    songListEntryIFrame.src = songListEntry.contentUrl;
    playlistList.appendChild(songListEntryIFrame);
  });
};

let hideSystemMessageDialogTimeout;
const showSystemMessage = (message) => {
  clearTimeout(hideSystemMessageDialogTimeout);
  systemMessageDialogContent.innerHTML = message;
  systemMessageDialog.hidden = false;
  hideSystemMessageDialogTimeout = setTimeout(() => {
    systemMessageDialog.hidden = true;
  }, 2000);
};

// ------------------------------------------------------------

// Functions: Top bar

const updateTopBar = (signedIn) => {
  if (signedIn) {
    topBarLoginLink.hidden = true;
    topBarSignUpLink.hidden = true;
    topBarAccountLink.hidden = false;
    topBarLogoutLink.hidden = false;
  } else {
    topBarAccountLink.hidden = true;
    topBarLogoutLink.hidden = true;
    topBarLoginLink.hidden = false;
    topBarSignUpLink.hidden = false;
  }
};

// ------------------------------------------------------------

// Functions: Home page

const appendNewEntryToPlaylistEditor = (contentUrl) => {
  const entryElementUrlInput = document.createElement('input');
  entryElementUrlInput.value = contentUrl;
  const entryElementMoveUpButton = document.createElement('button');
  entryElementMoveUpButton.addEventListener('click', () => {
    // TODO: entryElement should swap places with its previous sibling
  });
  entryElementMoveUpButton.innerHTML = '';
  const entryElementMoveDownButton = document.createElement('button');
  entryElementMoveDownButton.addEventListener('click', () => {
    // TODO: entryElement should swap places with its next sibling
  });
  entryElementMoveDownButton.innerHTML = '';
  const entryElementRemoveButton = document.createElement('button');
  entryElementRemoveButton.addEventListener('click', () => {
    homePlaylistEditorEntriesContainer.removeChild(entryElement);
  });
  entryElementRemoveButton.innerHTML = '';
  const entryElement = document.createElement('div');
  entryElement.appendChild(entryElementUrlInput);
  entryElement.appendChild(entryElementMoveUpButton);
  entryElement.appendChild(entryElementMoveDownButton);
  entryElement.appendChild(entryElementRemoveButton);
  homePlaylistEditorEntriesContainer.appendChild(entryElement);
};

const populatePlaylistEditor = async () => {
  homePlaylistEditorTitleInput.value = '';
  homePlaylistEditorEntriesContainer.innerHTML = '';
  try {
    const response = await fetch('http://localhost:8080/api/song-list/session');
    if (response.ok) {
      response.json()
      .then((songList) => {
        homePlaylistEditorTitleInput.value = songList.title;
        const entries = songList.entries;
        entries.forEach((entry) => {
          appendNewEntryToPlaylistEditor(entry.contentUrl);
        });
      });
    } else {
      showSystemMessage('Unable to fetch playlist');
    }
  } catch {
    showSystemMessage('Unable to fetch playlist');
  }
};

// ------------------------------------------------------------

// Configure event listeners: Top bar

topBarHomeLink.addEventListener('click', () => {
  navigateToHomePage();
});

topBarLoginLink.addEventListener('click', () => {
  navigateToLoginPage();
});

topBarSignUpLink.addEventListener('click', () => {
  navigateToSignUpPage();
});

topBarAccountLink.addEventListener('click', () => {
  navigateToAccountPage();
});

topBarLogoutLink.addEventListener('click', () => {
  fetch('http://localhost:8080/api/auth', {
    method: 'DELETE'
  })
  .then((response) => {
    if (response.ok) {
      updateTopBar(false);
      navigateToHomePage();
    } else {
      showSystemMessage('Unable to logout');
    }
  })
  .catch(() => {
    showSystemMessage('Unable to logout');
  });
});

topBarSearchInput.addEventListener('keypress', (event) => {
  if (event.key == 'Enter') {
    event.preventDefault();
    topBarSearchButton.click();
  }
});

topBarSearchButton.addEventListener('click', () => {
  const userName = extractInput(topBarSearchInput);
  if (userName == null) {
    return;
  }
  topBarSearchButton.disabled = true;
  fetch('http://localhost:8080/api/song-list/user-public-name/' + userName)
  .then((songListResponse) => {
    if (songListResponse.status == 200) {
      songListResponse.json()
      .then((songList) => {
        fetch('http://localhost:8080/api/user/id/' + songList.userId)
        .then((userResponse) => {
          if (userResponse.ok) {
            userResponse.json()
            .then((user) => {
              navigateToPlaylistPage(user, songList);
            });
          } else {
            showSystemMessage('Unable to fetch user data');
          }
        })
        .catch(() => {
          showSystemMessage('Unable to fetch user data');
        });
      });
    } else if (songListResponse.status == 404) {
      showSystemMessage('No such user');
    } else {
      showSystemMessage('Unable to fetch playlist data');
    }
  })
  .catch(() => {
    showSystemMessage('Unable to fetch playlist data');
  })
  .finally(() => {
    topBarSearchButton.disabled = false;
  });
});

// ------------------------------------------------------------

// Configure event listeners: Home page

homePlaylistEditorSubmitButton.addEventListener('click', () => {
  let songList = {
    title: extractInput(homePlaylistEditorTitleInput),
    entries: []
  };
  const entries = homePlaylistEditorEntriesContainer.children;
  for (let i = 0; i < entries.length; i++) {
    const contentUrl = extractInput(entries[i].firstElementChild);
    if (contentUrl != null) {
      songList.entries.push({
        contentUrl: contentUrl
      });
    }
  }
  fetch('http://localhost:8080/api/song-list/session', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(songList)
  })
  .then((response) => {
    if (response.status == 200) {
      showSystemMessage('Successfully saved playlist');
      populatePlaylistEditor();
    } else if (response.status == 400) {
      showSystemMessage('Invalid fields provided');
    } else {
      showSystemMessage('Unable to save playlist');
    }
  })
  .catch(() => {
    showSystemMessage('Unable to save playlist');
  });
});

homePlaylistEditorAddButton.addEventListener('click', () => {
  if (homePlaylistEditorEntriesContainer.children.length < 10) {
    appendNewEntryToPlaylistEditor(null);
  } else {
    showSystemMessage('Up to 10 songs are allowed');
  }
});

// ------------------------------------------------------------

// Configure event listeners: Login page

loginNameInput.addEventListener('keypress', (event) => {
  if (event.key == 'Enter') {
    event.preventDefault();
    loginPasswordInput.focus();
  }
});

loginPasswordInput.addEventListener('keypress', (event) => {
  if (event.key == 'Enter') {
    event.preventDefault();
    loginButton.click();
  }
});

loginButton.addEventListener('click', () => {
  const name = extractInput(loginNameInput);
  const password = extractInput(loginPasswordInput);
  if (name == null || password == null) {
    showSystemMessage('Missing required fields');
    return;
  }
  loginButton.disabled = true;
  loginLoadingOverlay.hidden = false;
  fetch('http://localhost:8080/api/auth', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      name: name,
      password: password
    })
  })
  .then((response) => {
    if (response.status == 200) {
      updateTopBar(true);
      navigateToHomePage();
    } else if (response.status == 400 || response.status == 403) {
      showSystemMessage('Invalid credentials');
    } else {
      showSystemMessage('Unable to login');
    }
  })
  .catch(() => {
    showSystemMessage('Unable to login');
  })
  .finally(() => {
    loginButton.disabled = false;
    loginLoadingOverlay.hidden = true;
  });
});

// ------------------------------------------------------------

// Configure event listeners: Sign up page

signUpLoginNameInput.addEventListener('keypress', (event) => {
  if (event.key == 'Enter') {
    event.preventDefault();
    signUpPasswordInput.focus();
  }
});

signUpPasswordInput.addEventListener('keypress', (event) => {
  if (event.key == 'Enter') {
    event.preventDefault();
    signUpPublicNameInput.focus();
  }
});

signUpPublicNameInput.addEventListener('keypress', (event) => {
  if (event.key == 'Enter') {
    event.preventDefault();
    signUpButton.click();
  }
});

signUpButton.addEventListener('click', () => {
  const loginName = extractInput(signUpLoginNameInput);
  const password = extractInput(signUpPasswordInput);
  const publicName = extractInput(signUpPublicNameInput);
  if (loginName == null || password == null || publicName == null) {
    showSystemMessage('Missing required fields');
    return;
  }
  signUpButton.disabled = true;
  signUpLoadingOverlay.hidden = false;
  fetch('http://localhost:8080/api/user', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      loginName: loginName,
      password: password,
      publicName: publicName
    })
  })
  .then((response) => {
    if (response.status == 200) {
      fetch('http://localhost:8080/api/auth', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          name: loginName,
          password: password
        })
      })
      .then((response) => {
        if (response.ok) {
          fetch('http://localhost:8080/api/song-list', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              title: 'Top 10',
              entries: []
            })
          })
          .then((response) => {
            if (response.ok) {
              updateTopBar(true);
              navigateToHomePage();
            } else {
              showSystemMessage('Unable to create song list');
            }
          })
          .catch(() => {
            showSystemMessage('Unable to create song list');
          });
        } else {
          showSystemMessage('Account has been created but unable to login');
        }
      })
      .catch (() => {
        showSystemMessage('Account has been created but unable to login');
      });
    } else if (response.status == 403) {
      showSystemMessage('Invalid credentials');
    } else {
      showSystemMessage('Unable to sign up');
    }
  })
  .catch(() => {
    showSystemMessage('Unable to sign up');
  })
  .finally(() => {
    signUpButton.disabled = false;
    signUpLoadingOverlay.hidden = true;
  });
});

// ------------------------------------------------------------

// Configure event listeners: Account page

accountLoginNameInput.addEventListener('keypress', (event) => {
  if (event.key == 'Enter') {
    event.preventDefault();
    accountPasswordInput.focus();
  }
});

accountPasswordInput.addEventListener('keypress', (event) => {
  if (event.key == 'Enter') {
    event.preventDefault();
    accountPublicNameInput.focus();
  }
});

accountPublicNameInput.addEventListener('keypress', (event) => {
  if (event.key == 'Enter') {
    event.preventDefault();
    accountUpdateButton.click();
  }
});

accountUpdateButton.addEventListener('click', () => {
  accountUpdateButton.disabled = true;
  accountDeleteButton.disabled = true;
  accountLoadingOverlay.hidden = false;
  const loginName = extractInput(accountLoginNameInput);
  const password = extractInput(accountPasswordInput);
  const publicName = extractInput(accountPublicNameInput);
  fetch('http://localhost:8080/api/user/session', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      loginName: loginName,
      password: password,
      publicName: publicName
    })
  })
  .then((response) => {
    if (response.ok) {
      clearPageContents();
      fetch('http://localhost:8080/api/user/session')
      .then((response) => {
        if (response.ok) {
          response.json()
          .then((data) => {
            accountLoginNameInput.value = data.loginName;
            accountPublicNameInput.value = data.publicName;
          });
        } else {
          showSystemMessage('Account successfully updated but unable to fetch account data');
        }
      })
      .catch(() =>  {
        showSystemMessage('Account successfully updated but unable to fetch account data');
      })
      .finally(() => {
        showSystemMessage('Account successfully updated');
        accountUpdateButton.disabled = false;
        accountDeleteButton.disabled = false;
        accountLoadingOverlay.hidden = true;
      });
    } else {
      if (response.status == 400) {
        showSystemMessage('Invalid values provided');
      } else if (response.status == 409) {
        showSystemMessage('The provided login ID or public name already exists');
      } else {
        showSystemMessage('Unable to update account');
      }
      accountUpdateButton.disabled = false;
      accountDeleteButton.disabled = false;
      accountLoadingOverlay.hidden = true;
    }
  })
  .catch(() => {
    showSystemMessage('Unable to update account');
    accountUpdateButton.disabled = false;
    accountDeleteButton.disabled = false;
    accountLoadingOverlay.hidden = true;
  });
});

accountDeleteButton.addEventListener('click', () => {
  accountUpdateButton.disabled = true;
  accountDeleteButton.disabled = true;
  accountLoadingOverlay.hidden = false;
  fetch('http://localhost:8080/api/user/session', {
    method: 'DELETE'
  })
  .then((response) => {
    if (response.ok) {
      fetch('http://localhost:8080/api/auth', {
        method: 'DELETE'
      })
      .finally(() => {
        showSystemMessage('Successfully deleted account');
        updateTopBar(false);
        navigateToHomePage();
      });
    } else {
      showSystemMessage('Unable to delete account');
    }
  })
  .catch(() => {
    showSystemMessage('Unable to delete account');
  })
  .finally(() => {
    accountUpdateButton.disabled = false;
    accountDeleteButton.disabled = false;
    accountLoadingOverlay.hidden = true;
  });
});

// ------------------------------------------------------------

// Initialize

fetch('http://localhost:8080/api/user/session')
.then((response) => {
  if (response.ok) {
    updateTopBar(true);
  } else {
    updateTopBar(false);
  }
})
.catch(() => {
  updateTopBar(false);
});

// navigateToHomePage();

// TEST START

// navigateToPlaylistPage({
//   publicName: 'Hugh'
// }, {
//   title: 'Hugh-kun no sugoi playlist',
//   entries: [
//     {
//       contentUrl: 'https://www.youtube-nocookie.com/embed/1Ys6C1QMNvI'
//     },
//     {
//       contentUrl: 'https://www.youtube-nocookie.com/embed/T7m9xx6CGTs'
//     },
//     {
//       contentUrl: 'https://w.soundcloud.com/player/?url=https%3A%2F%2Fsoundcloud.com%2Fyomoha%2F2o22o428&visual=true&show_comments=false&show_teaser=false&hide_related=true'
//     }
//   ]
// });

homePageRoot.hidden = false;
homeUnauthenticatedRoot.hidden = true;
homeAuthenticatedRoot.hidden = false;
appendNewEntryToPlaylistEditor('1');
appendNewEntryToPlaylistEditor('2');
appendNewEntryToPlaylistEditor('3');
appendNewEntryToPlaylistEditor('4');

// TEST FINISH