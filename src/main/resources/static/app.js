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
const homePlaylistEditorLoadingOverlay = document.getElementById('home-playlist-editor-loading-overlay');
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
const playlistLoadingOverlay = document.getElementById('playlist-loading-overlay');

const systemMessageDialog = document.getElementById('system-message-dialog');
const systemMessageDialogContent = document.getElementById('system-message-dialog-content');

// ------------------------------------------------------------

// Functions: Utility

const getSessionToken = () => {
  const cookies = document.cookie.split(';');
  cookies.forEach(cookie => {
    const trimmedCookie = cookie.trim();
    if (trimmedCookie.startsWith('session=')) {
      return trimmedCookie.substring(8);
    }
  });
  return null;
};

const clearSession = () => {
  document.cookie = 'session=; Max-Age=0';
};

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
  homePlaylistEditorLoadingOverlay.hidden = true;
  homeLoadingOverlay.hidden = true;
  loginNameInput.value = '';
  loginPasswordInput.value = '';
  loginLoadingOverlay.hidden = true;
  signUpLoginNameInput.value = '';
  signUpPasswordInput.value = '';
  signUpPublicNameInput.value = '';
  signUpLoadingOverlay.hidden = true;
  signUpPasswordWarning.hidden = true;
  accountLoginNameInput.value = '';
  accountPasswordInput.value = '';
  accountPublicNameInput.value = '';
  accountLoadingOverlay.hidden = true;
  playlistTitle.innerHTML = '';
  playlistUserPublicName.innerHTML = '';
  playlistList.innerHTML = '';
  playlistLoadingOverlay.hidden = true;
};

const navigateToHomePage = async (pushToHistory) => {
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
  const result = await api.readUserBySession();
  if (result.status == 200) {
    homeAuthenticatedRoot.hidden = false;
    await populateHomePlaylist();
    homePlaylistRoot.hidden = false;
  } else {
    homeUnauthenticatedRoot.hidden = false;
  }
  homeLoadingOverlay.hidden = true;
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

const navigateToAccountPage = async (pushToHistory) => {
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
  accountLoadingOverlay.hidden = false;
  const result = await api.readUserBySession();
  if (result.status == 200) {
    accountLoginNameInput.value = result.data.loginName;
    accountPublicNameInput.value = result.data.publicName;
  } else {
    showSystemMessage('Unable to get account data');
  }
  accountLoadingOverlay.hidden = true;
};

const navigateToPlaylistPage = async (pushToHistory, userName) => {
  if (pushToHistory) {
    history.pushState({
      path: '/playlist/' + userName.toLowerCase()
    }, '', '/playlist/' + userName.toLowerCase());
  }
  document.title = 'Top Ten';
  clearPageContents();
  homePageRoot.hidden = true;
  loginPageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  accountPageRoot.hidden = true;
  playlistPageRoot.hidden = false;
  playlistLoadingOverlay.hidden = false;
  const readSongListByUserPublicNameResult = await api.readSongListByUserPublicName(userName);
  if (readSongListByUserPublicNameResult.status == 200) {
    const readUserByPublicNameResult = await api.readUserByPublicName(userName);
    if (readUserByPublicNameResult.status == 200) {
      document.title = 'Top Ten - ' + readUserByPublicNameResult.data.publicName;
      playlistUserPublicName.innerHTML = readUserByPublicNameResult.data.publicName;
    } else {
      showSystemMessage('Unable to get user data');
    }
    playlistTitle.innerHTML = readSongListByUserPublicNameResult.data.title;
    readSongListByUserPublicNameResult.data.entries.forEach((songListEntry) => {
      const songListEntryIFrame = document.createElement('iframe');
      songListEntryIFrame.src = songListEntry.contentUrl;
      playlistList.appendChild(songListEntryIFrame);
    });
  } else {
    showSystemMessage('Unable to get playlist data');
  }
  playlistLoadingOverlay.hidden = true;
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
    showSystemMessage('Unable to get playlist data');
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
    showSystemMessage('Unable to get playlist data');
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

topBarLogoutLink.addEventListener('click', async () => {
  api.logout();
  clearSession();
  updateTopBar(null);
  navigateToHomePage(true);
});

topBarSearchInput.addEventListener('keypress', (event) => {
  if (event.key == 'Enter') {
    event.preventDefault();
    topBarSearchButton.click();
  }
});

topBarSearchButton.addEventListener('click', async () => {
  topBarSearchButton.disabled = true;
  // TODO: Start indicating that task is processing
  const userName = nullIfEmpty(topBarSearchInput.value);
  if (userName == null) {
    return;
  }
  const result = await api.readUserByPublicName(userName);
  switch (result.status) {
    case 200:
      await navigateToPlaylistPage(true, userName);
      break;
    case 404:
      showSystemMessage('No such user');
      break;
    default:
      showSystemMessage('Unable to search for user');
  }
  // TODO: Stop indicating that task is processing
  topBarSearchButton.disabled = false;
});

// ------------------------------------------------------------

// Event listeners: Home page

homePlaylistEditButton.addEventListener('click', async () => {
  homePlaylistEditButton.disabled = true;
  homeLoadingOverlay.hidden = false;
  homePlaylistRoot.hidden = true;
  homePlaylistTitle.innerHTML = '';
  homePlaylistEntriesContainer.innerHTML = '';
  await populatePlaylistEditor();
  homePlaylistEditorRoot.hidden = false;
  homeLoadingOverlay.hidden = true;
  homePlaylistEditButton.disabled = false;
});

homePlaylistShareButton.addEventListener('click', async () => {
  homePlaylistShareButton.disabled = true;
  homePlaylistOverlay.hidden = false;
  homePlaylistShareLink.innerHTML = 'Loading..';
  homePlaylistShareContainer.hidden = false;
  const result = await api.readUserBySession();
  if (result.status == 200) {
    homePlaylistShareLink.innerHTML = api.origin + '/playlist/' + result.data.publicName.toLowerCase();
  } else {
    homePlaylistShareLink.innerHTML = 'Unable to load share link';
  }
  homePlaylistShareButton.disabled = false;
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

homePlaylistEditorSubmitButton.addEventListener('click', async () => {
  homePlaylistEditorSubmitButton.disabled = true;
  homePlaylistEditorLoadingOverlay.hidden = false;
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
  const result = await api.updateSongListBySession(songList);
  switch (result.status) {
    case 200:
      showSystemMessage('Successfully saved playlist');
      await populatePlaylistEditor();
      break;
    case 400:
      showSystemMessage('Invalid fields provided or title too long');
      break;
    default:
      showSystemMessage('Unable to save playlist');
  }
  homePlaylistEditorLoadingOverlay.hidden = true;
  homePlaylistEditorSubmitButton.disabled = false;
});

homePlaylistEditorBackButton.addEventListener('click', async () => {
  homePlaylistEditorBackButton.disabled = true;
  homeLoadingOverlay.hidden = false;
  homePlaylistEditorRoot.hidden = true;
  await populateHomePlaylist();
  homePlaylistRoot.hidden = false;
  homeLoadingOverlay.hidden = true;
  homePlaylistEditorBackButton.disabled = false;
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

loginButton.addEventListener('click', async () => {
  loginButton.disabled = true;
  loginLoadingOverlay.hidden = false;
  const name = nullIfEmpty(loginNameInput.value);
  const password = nullIfEmpty(loginPasswordInput.value);
  if (name == null || password == null) {
    showSystemMessage('Missing required fields');
    return;
  }
  const loginResult = await api.login({
    name: name,
    password: password
  });
  switch (loginResult.status) {
    case 200:
      const readUserBySessionResult = await api.readUserBySession();
      updateTopBar(readUserBySessionResult.data);
      navigateToHomePage(true);
      break;
    case 400, 403:
      showSystemMessage('Invalid credentials');
      break;
    default:
      showSystemMessage('Unable to login');
  }
  loginLoadingOverlay.hidden = true;
  loginButton.disabled = false;
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

signUpButton.addEventListener('click', async () => {
  signUpButton.disabled = true;
  signUpLoadingOverlay.hidden = false;
  const loginName = nullIfEmpty(signUpLoginNameInput.value);
  const password = nullIfEmpty(signUpPasswordInput.value);
  const publicName = nullIfEmpty(signUpPublicNameInput.value);
  if (loginName == null || password == null || publicName == null) {
    showSystemMessage('Missing required fields');
    return;
  }
  const createUserResult = await api.createUser({
    loginName: loginName,
    password: password,
    publicName: publicName
  });
  if (createUserResult.status == 200) {
    const loginResult = await api.login({
      name: loginName,
      password: password
    });
    if (loginResult.status == 200) {
      const readUserBySessionResult = await api.readUserBySession();
      if (readUserBySessionResult.status == 200) {
        updateTopBar(readUserBySessionResult.data);
      } else {
        showSystemMessage('Unable to get account data');
      }
      const createSongListResult = await api.createSongList({
        title: 'Top Ten',
        entries: []
      });
      if (createSongListResult.status == 200) {
        navigateToHomePage(true);
      } else {
        showSystemMessage('Unable to create song list');
      }
    } else {
      showSystemMessage('Account has been created but unable to login');
    }
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
  }
  signUpLoadingOverlay.hidden = true;
  signUpButton.disabled = false;
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

accountUpdateButton.addEventListener('click', async () => {
  accountUpdateButton.disabled = true;
  accountLoadingOverlay.hidden = false;
  const loginName = nullIfEmpty(accountLoginNameInput.value);
  const password = nullIfEmpty(accountPasswordInput.value);
  const publicName = nullIfEmpty(accountPublicNameInput.value);
  const updateUserBySessionResult = await api.updateUserBySession({
    loginName: loginName,
    password: password,
    publicName: publicName
  });
  if (updateUserBySessionResult.status == 200) {
    const readUserBySessionResult = await api.readUserBySession();
    if (readUserBySessionResult.status == 200) {
      showSystemMessage('Account successfully updated');
      clearPageContents();
      accountLoginNameInput.value = readUserBySessionResult.data.loginName;
      accountPublicNameInput.value = readUserBySessionResult.data.publicName;
      updateTopBar(readUserBySessionResult.data);
    } else {
      showSystemMessage('Account successfully updated but unable to get account data');
    }
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
  }
  accountLoadingOverlay.hidden = true;
  accountUpdateButton.disabled = false;
});

accountDeleteButton.addEventListener('click', async () => {
  accountDeleteButton.disabled = true;
  accountLoadingOverlay.hidden = false;
  const result = await api.deleteUserBySession();
  if (result.status == 200) {
    showSystemMessage('Successfully deleted account');
    api.logout();
    clearSession();
    updateTopBar(null);
    navigateToHomePage(true);
  } else {
    showSystemMessage('Unable to delete account');
  }
  accountLoadingOverlay.hidden = true;
  accountDeleteButton.disabled = false;
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