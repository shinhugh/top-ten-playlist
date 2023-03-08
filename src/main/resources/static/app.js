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
const homePlaylistRoot = document.getElementById('home-playlist-root');
const homePlaylistTitle = document.getElementById('home-playlist-title');
const homePlaylistEditButton = document.getElementById('home-playlist-edit-button');
const homePlaylistShareButton = document.getElementById('home-playlist-share-button');
const homePlaylistEntriesContainer = document.getElementById('home-playlist-entries-container');
const homePlaylistOverlay = document.getElementById('home-playlist-overlay');
const homePlaylistShareContainer = document.getElementById('home-playlist-share-container');
const homePlaylistShareLink = document.getElementById('home-playlist-share-link');
const homePlaylistShareCloseButton = document.getElementById('home-playlist-share-close-button');
const homePlaylistEditorRoot = document.getElementById('home-playlist-editor-root');
const homePlaylistEditorTitleInput = document.getElementById('home-playlist-editor-title-input');
const homePlaylistEditorEntriesContainer = document.getElementById('home-playlist-editor-entries-container');
const homePlaylistEditorAddButton = document.getElementById('home-playlist-editor-add-button');
const homePlaylistEditorClearButton = document.getElementById('home-playlist-editor-clear-button');
const homePlaylistEditorSubmitButton = document.getElementById('home-playlist-editor-submit-button');
const homePlaylistEditorBackButton = document.getElementById('home-playlist-editor-back-button');
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
const signUpPasswordWarning = document.getElementById('sign-up-password-warning');

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

const nullIfEmpty = (string) => {
  if (string == null) {
    return null;
  }
  return string.length == 0 ? null : string;
};

// ------------------------------------------------------------

// Functions: Global

const clearPageContents = () => {
  homeUnauthenticatedRoot.hidden = true;
  homeAuthenticatedRoot.hidden = true;
  homePlaylistRoot.hidden = true;
  homePlaylistTitle.innerHTML = '';
  homePlaylistEntriesContainer.innerHTML = '';
  homePlaylistOverlay.hidden = true;
  homePlaylistShareContainer.hidden = true;
  homePlaylistShareLink.innerHTML = '';
  homePlaylistEditorRoot.hidden = true;
  homePlaylistEditorTitleInput.value = '';
  homePlaylistEditorEntriesContainer.innerHTML = '';
  loginNameInput.value = '';
  loginPasswordInput.value = '';
  signUpLoginNameInput.value = '';
  signUpPasswordInput.value = '';
  signUpPublicNameInput.value = '';
  signUpPasswordWarning.hidden = true;
  accountLoginNameInput.value = '';
  accountPasswordInput.value = '';
  accountPublicNameInput.value = '';
  playlistTitle.innerHTML = '';
  playlistUserPublicName.innerHTML = '';
  playlistList.innerHTML = '';
};

const navigateToHomePage = (pushToHistory) => {
  if (pushToHistory) {
    history.pushState({
      path: '/'
    }, '', '/');
  }
  document.title = 'Top Ten';
  clearPageContents();
  loginPageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  accountPageRoot.hidden = true;
  homePageRoot.hidden = false;
  homeLoadingOverlay.hidden = false;
  api.readUserBySession()
  .then((result) => {
    if (result.status == 200) {
      homeAuthenticatedRoot.hidden = false;
      populateHomePlaylist()
      .finally(() => {
        homePlaylistRoot.hidden = false;
        homeLoadingOverlay.hidden = true;
      });
    } else {
      homeUnauthenticatedRoot.hidden = false;
      homeLoadingOverlay.hidden = true;
    }
  });
};

const navigateToLoginPage = (pushToHistory) => {
  if (pushToHistory) {
    history.pushState({
      path: '/login'
    }, '', '/login');
  }
  document.title = 'Login';
  clearPageContents();
  homePageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  accountPageRoot.hidden = true;
  loginPageRoot.hidden = false;
  loginNameInput.focus();
};

const navigateToSignUpPage = (pushToHistory) => {
  if (pushToHistory) {
    history.pushState({
      path: '/signup'
    }, '', '/signup');
  }
  document.title = 'Sign Up';
  clearPageContents();
  homePageRoot.hidden = true;
  loginPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  accountPageRoot.hidden = true;
  signUpPageRoot.hidden = false;
  showPasswordWarning();
  signUpLoginNameInput.focus();
};

const navigateToAccountPage = (pushToHistory) => {
  if (pushToHistory) {
    history.pushState({
      path: '/account'
    }, '', '/account');
  }
  document.title = 'Account';
  clearPageContents();
  homePageRoot.hidden = true;
  loginPageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  accountPageRoot.hidden = false;
  accountUpdateButton.disabled = true;
  accountLoadingOverlay.hidden = false;
  api.readUserBySession()
  .then((result) =>  {
    if (result.status == 200) {
      accountLoginNameInput.value = result.data.loginName;
      accountPublicNameInput.value = result.data.publicName;
    } else {
      showSystemMessage('Unable to get account data');
    }
    accountUpdateButton.disabled = false;
    accountLoadingOverlay.hidden = true;
  });
};

const navigateToPlaylistPage = (pushToHistory, userName) => {
  if (pushToHistory) {
    history.pushState({
      path: '/playlist/' + userName
    }, '', '/playlist/' + userName);
  }
  document.title = 'Playlist - ' + userName;
  clearPageContents();
  homePageRoot.hidden = true;
  loginPageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  accountPageRoot.hidden = true;
  playlistPageRoot.hidden = false;
  api.readSongListByUserPublicName(userName)
  .then((result) => {
    if (result.status == 200) {
      playlistTitle.innerHTML = result.data.title;
      playlistUserPublicName.innerHTML = userName;
      result.data.entries.forEach((songListEntry) => {
        const songListEntryIFrame = document.createElement('iframe');
        songListEntryIFrame.src = songListEntry.contentUrl;
        playlistList.appendChild(songListEntryIFrame);
      });
    } else {
      showSystemMessage('Unable to get playlist data');
    }
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

const updateTopBar = (user) => {
  if (user == null) {
    topBarAccountLink.hidden = true;
    topBarLogoutLink.hidden = true;
    topBarLoginLink.hidden = false;
    topBarSignUpLink.hidden = false;
    topBarAccountLink.innerHTML = '';
  } else {
    topBarLoginLink.hidden = true;
    topBarSignUpLink.hidden = true;
    topBarAccountLink.hidden = false;
    topBarLogoutLink.hidden = false;
    topBarAccountLink.innerHTML = user.publicName;
  }
};

// ------------------------------------------------------------

// Functions: Home page

const populateHomePlaylist = async () => {
  homePlaylistTitle.innerHTML = '';
  homePlaylistEntriesContainer.innerHTML = '';
  const result = await api.readSongListBySession();
  if (result.status == 200) {
    homePlaylistTitle.innerHTML = result.data.title;
    result.data.entries.forEach((songListEntry) => {
      const songListEntryIFrame = document.createElement('iframe');
      songListEntryIFrame.src = songListEntry.contentUrl;
      homePlaylistEntriesContainer.appendChild(songListEntryIFrame);
    });
  } else {
    showSystemMessage('Unable to get playlist');
  }
};

const appendNewEntryToPlaylistEditor = (contentUrl) => {
  const entryElementUrlInput = document.createElement('input');
  entryElementUrlInput.value = contentUrl;
  const entryElementMoveUpButton = document.createElement('button');
  entryElementMoveUpButton.addEventListener('click', () => {
    if (entryElement.previousSibling != null) {
      homePlaylistEditorEntriesContainer.insertBefore(entryElement, entryElement.previousSibling);
    }
  });
  entryElementMoveUpButton.innerHTML = '';
  const entryElementMoveDownButton = document.createElement('button');
  entryElementMoveDownButton.addEventListener('click', () => {
    if (entryElement.nextSibling != null) {
      homePlaylistEditorEntriesContainer.insertBefore(entryElement.nextSibling, entryElement);
    }
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
  const result = await api.readSongListBySession();
  if (result.status == 200) {
    homePlaylistEditorTitleInput.value = result.data.title;
    result.data.entries.forEach((entry) => {
      appendNewEntryToPlaylistEditor(entry.contentUrl);
    });
  } else {
    showSystemMessage('Unable to get playlist');
  }
};

// ------------------------------------------------------------

// Functions: Sign up page

let signUpPasswordWarningTimeout;
const showPasswordWarning = () => {
  clearTimeout(signUpPasswordWarningTimeout);
  signUpPasswordWarning.hidden = false;
  signUpPasswordWarningTimeout = setTimeout(() => {
    signUpPasswordWarning.hidden = true;
  }, 5000);
};

// ------------------------------------------------------------

// Event listeners: Window

window.addEventListener('popstate', (event) => {
  if (event.state.path == '/') {
    navigateToHomePage(false);
  } else if (event.state.path == '/login') {
    navigateToLoginPage(false);
  } else if (event.state.path == '/signup') {
    navigateToSignUpPage(false);
  } else if (event.state.path == '/account') {
    navigateToAccountPage(false);
  } else if (event.state.path.startsWith('/playlist/')) {
    const userName = event.state.path.substring(10);
    navigateToPlaylistPage(false, userName);
  }
});

// ------------------------------------------------------------

// Event listeners: Top bar

topBarHomeLink.addEventListener('click', () => {
  navigateToHomePage(true);
});

topBarLoginLink.addEventListener('click', () => {
  navigateToLoginPage(true);
});

topBarSignUpLink.addEventListener('click', () => {
  navigateToSignUpPage(true);
});

topBarAccountLink.addEventListener('click', () => {
  navigateToAccountPage(true);
});

topBarLogoutLink.addEventListener('click', () => {
  api.logout()
  .then((result) => {
    if (result.status == 200) {
      updateTopBar(null);
      navigateToHomePage(true);
    } else {
      showSystemMessage('Unable to logout');
    }
  });
});

topBarSearchInput.addEventListener('keypress', (event) => {
  if (event.key == 'Enter') {
    event.preventDefault();
    topBarSearchButton.click();
  }
});

topBarSearchButton.addEventListener('click', () => {
  const userName = nullIfEmpty(topBarSearchInput.value);
  if (userName == null) {
    return;
  }
  topBarSearchButton.disabled = true;
  api.readUserByPublicName(userName)
  .then((result) => {
    switch (result.status) {
      case 200:
        navigateToPlaylistPage(true, userName);
        break;
      case 404:
        showSystemMessage('No such user');
        break;
      default:
        showSystemMessage('Unable to search for user');
    }
    topBarSearchButton.disabled = false;
  });
});

// ------------------------------------------------------------

// Event listeners: Home page

homePlaylistEditButton.addEventListener('click', () => {
  homeLoadingOverlay.hidden = false;
  homePlaylistRoot.hidden = true;
  populatePlaylistEditor()
  .finally(() => {
    homePlaylistEditorRoot.hidden = false;
    homeLoadingOverlay.hidden = true;
  });
});

homePlaylistShareButton.addEventListener('click', () => {
  homePlaylistOverlay.hidden = false;
  homePlaylistShareLink.innerHTML = 'Loading..';
  homePlaylistShareContainer.hidden = false;
  api.readUserBySession()
  .then((result) => {
    if (result.status == 200) {
      homePlaylistShareLink.innerHTML = api.origin + '/playlist/' + result.data.publicName.toLowerCase();
    } else {
      homePlaylistShareLink.innerHTML = 'Unable to load share link';
    }
  });
});

homePlaylistOverlay.addEventListener('click', (event) => {
  if (event.target != homePlaylistOverlay) {
    return;
  }
  homePlaylistOverlay.hidden = true;
  homePlaylistShareContainer.hidden = true;
  homePlaylistShareLink.innerHTML = '';
});

homePlaylistShareLink.addEventListener('click', () => {
  homePlaylistShareLink.select();
});

homePlaylistShareCloseButton.addEventListener('click', () => {
  homePlaylistOverlay.hidden = true;
  homePlaylistShareContainer.hidden = true;
  homePlaylistShareLink.innerHTML = '';
});

homePlaylistEditorAddButton.addEventListener('click', () => {
  if (homePlaylistEditorEntriesContainer.children.length < 10) {
    appendNewEntryToPlaylistEditor(null);
  } else {
    showSystemMessage('Up to 10 songs are allowed');
  }
});

homePlaylistEditorClearButton.addEventListener('click', () => {
  homePlaylistEditorEntriesContainer.innerHTML = '';
});

homePlaylistEditorSubmitButton.addEventListener('click', () => {
  let songList = {
    title: nullIfEmpty(homePlaylistEditorTitleInput.value),
    entries: []
  };
  const entries = homePlaylistEditorEntriesContainer.children;
  for (let i = 0; i < entries.length; i++) {
    const contentUrl = nullIfEmpty(entries[i].firstElementChild.value);
    if (contentUrl != null) {
      songList.entries.push({
        contentUrl: contentUrl
      });
    }
  }
  api.updateSongListBySession(songList)
  .then((result) => {
    switch (result.status) {
      case 200:
        showSystemMessage('Successfully saved playlist');
        populatePlaylistEditor();
        break;
      case 400:
        showSystemMessage('Invalid fields provided or title too long');
        break;
      default:
        showSystemMessage('Unable to save playlist');
    }
  });
});

homePlaylistEditorBackButton.addEventListener('click', () => {
  homeLoadingOverlay.hidden = false;
  homePlaylistEditorRoot.hidden = true;
  populateHomePlaylist()
  .finally(() => {
    homePlaylistRoot.hidden = false;
    homeLoadingOverlay.hidden = true;
  });
});

// ------------------------------------------------------------

// Event listeners: Login page

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
  const name = nullIfEmpty(loginNameInput.value);
  const password = nullIfEmpty(loginPasswordInput.value);
  if (name == null || password == null) {
    showSystemMessage('Missing required fields');
    return;
  }
  loginButton.disabled = true;
  loginLoadingOverlay.hidden = false;
  api.login({
    name: name,
    password: password
  })
  .then((loginResult) => {
    switch (loginResult.status) {
      case 200:
        api.readUserBySession()
        .then((readUserBySessionResult) => {
          updateTopBar(readUserBySessionResult.data);
          navigateToHomePage(true);
        });
        break;
      case 400, 403:
        showSystemMessage('Invalid credentials');
        break;
      default:
        showSystemMessage('Unable to login');
    }
    loginButton.disabled = false;
    loginLoadingOverlay.hidden = true;
  });
});

// ------------------------------------------------------------

// Event listeners: Sign up page

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
  const loginName = nullIfEmpty(signUpLoginNameInput.value);
  const password = nullIfEmpty(signUpPasswordInput.value);
  const publicName = nullIfEmpty(signUpPublicNameInput.value);
  if (loginName == null || password == null || publicName == null) {
    showSystemMessage('Missing required fields');
    return;
  }
  signUpButton.disabled = true;
  signUpLoadingOverlay.hidden = false;
  api.createUser({
    loginName: loginName,
    password: password,
    publicName: publicName
  })
  .then((createUserResult) => {
    if (createUserResult.status == 200) {
      api.login({
        name: loginName,
        password: password
      })
      .then((loginResult) => {
        if (loginResult.status == 200) {
          api.createSongList({
            title: 'Top Ten',
            entries: []
          })
          .then((createSongListResult) => {
            if (createSongListResult.status == 200) {
              api.readUserBySession()
              .then((readUserBySessionResult) => {
                updateTopBar(readUserBySessionResult.data);
                navigateToHomePage(true);
              });
            } else {
              showSystemMessage('Unable to create song list');
            }
            signUpButton.disabled = false;
            signUpLoadingOverlay.hidden = true;
          });
        } else {
          showSystemMessage('Account has been created but unable to login');
          signUpButton.disabled = false;
          signUpLoadingOverlay.hidden = true;
        }
      });
    } else {
      switch(createUserResult.status) {
        case 400:
          showSystemMessage('Invalid characters provided or length requirements not met');
          break;
        case 409:
          showSystemMessage('Provided login ID or public name is already taken');
          break;
        default:
          showSystemMessage('Unable to sign up');
      }
      signUpButton.disabled = false;
      signUpLoadingOverlay.hidden = true;
    }
  });
});

// ------------------------------------------------------------

// Event listeners: Account page

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
  const loginName = nullIfEmpty(accountLoginNameInput.value);
  const password = nullIfEmpty(accountPasswordInput.value);
  const publicName = nullIfEmpty(accountPublicNameInput.value);
  api.updateUserBySession({
    loginName: loginName,
    password: password,
    publicName: publicName
  })
  .then((updateUserBySessionResult) => {
    if (updateUserBySessionResult.status == 200) {
      api.readUserBySession()
      .then((readUserBySessionResult) => {
        if (readUserBySessionResult.status == 200) {
          showSystemMessage('Account successfully updated');
          clearPageContents();
          accountLoginNameInput.value = readUserBySessionResult.data.loginName;
          accountPublicNameInput.value = readUserBySessionResult.data.publicName;
        } else {
          showSystemMessage('Account successfully updated but unable to get account data');
        }
        accountUpdateButton.disabled = false;
        accountDeleteButton.disabled = false;
        accountLoadingOverlay.hidden = true;
      });
    } else {
      switch (updateUserBySessionResult.status) {
        case 400:
          showSystemMessage('Invalid characters provided or length requirements not met');
          break;
        case 409:
          showSystemMessage('The provided login ID or public name already exists');
          break;
        default:
          showSystemMessage('Unable to update account');
      }
      accountUpdateButton.disabled = false;
      accountDeleteButton.disabled = false;
      accountLoadingOverlay.hidden = true;
    }
  });
});

accountDeleteButton.addEventListener('click', () => {
  accountUpdateButton.disabled = true;
  accountDeleteButton.disabled = true;
  accountLoadingOverlay.hidden = false;
  api.deleteUserBySession()
  .then((result) => {
    if (result.status == 200) {
      api.logout()
      .finally(() => {
        showSystemMessage('Successfully deleted account');
        updateTopBar(null);
        navigateToHomePage(true);
        accountUpdateButton.disabled = false;
        accountDeleteButton.disabled = false;
        accountLoadingOverlay.hidden = true;
      });
    } else {
      showSystemMessage('Unable to delete account');
      accountUpdateButton.disabled = false;
      accountDeleteButton.disabled = false;
      accountLoadingOverlay.hidden = true;
    }
  });
});

// ------------------------------------------------------------

// Initialization

api.readUserBySession()
.then((result) => {
  if (result.status == 200) {
    updateTopBar(result.data);
  } else {
    updateTopBar(null);
  }
});
history.replaceState({
  path: initialPath
}, '', initialPath);
switch (initialPath) {
  case '/':
    navigateToHomePage(false);
    break;
  case '/login':
    navigateToLoginPage(false);
    break;
  case '/signup':
    navigateToSignUpPage(false);
    break;
  case '/account':
    navigateToAccountPage(false);
    break;
  default:
    if (initialPath.startsWith('/playlist/')) {
      const userPublicName = initialPath.substring(10);
      navigateToPlaylistPage(false, userPublicName);
    }
}