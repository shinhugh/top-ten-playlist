// DOM elements

const topBarHomeLink = document.getElementById('top-bar-home-link');
const topBarSearchInput = document.getElementById('top-bar-search-input');
const topBarSearchButton = document.getElementById('top-bar-search-button');
const topBarLoginLink = document.getElementById('top-bar-login-link');
const topBarSignUpLink = document.getElementById('top-bar-sign-up-link');
const topBarAccountLink = document.getElementById('top-bar-account-link');
const topBarLogoutLink = document.getElementById('top-bar-logout-link');

const homePageRoot = document.getElementById('home-page-root');

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
const accountButton = document.getElementById('account-button');
const accountLoadingOverlay = document.getElementById('account-loading-overlay');

const playlistPageRoot = document.getElementById('playlist-page-root');

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

const clearPageInputs = () => {
  loginNameInput.value = '';
  loginPasswordInput.value = '';
  signUpLoginNameInput.value = '';
  signUpPasswordInput.value = '';
  signUpPublicNameInput.value = '';
};

const navigateToHomePage = () => {
  clearPageInputs();
  loginPageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  accountPageRoot.hidden = true;
  homePageRoot.hidden = false;
};

const navigateToLoginPage = () => {
  clearPageInputs();
  homePageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  accountPageRoot.hidden = true;
  loginPageRoot.hidden = false;
};

const navigateToSignUpPage = () => {
  clearPageInputs();
  homePageRoot.hidden = true;
  loginPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  accountPageRoot.hidden = true;
  signUpPageRoot.hidden = false;
};

const navigateToAccountPage = () => {
  clearPageInputs();
  homePageRoot.hidden = true;
  loginPageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  accountPageRoot.hidden = false;
  accountButton.disabled = true;
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
    accountButton.disabled = false;
    accountLoadingOverlay.hidden = true;
  });
};

const navigateToPlaylistPage = () => {
  clearPageInputs();
  homePageRoot.hidden = true;
  loginPageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  accountPageRoot.hidden = true;
  playlistPageRoot.hidden = false;
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
      navigateToLoginPage();
    } else {
      showSystemMessage('Unable to logout');
    }
  });
});

topBarSearchButton.addEventListener('click', () => {
  const userName = extractInput(topBarSearchInput);
  if (userName == null) {
    return;
  }
  fetch('http://localhost:8080/api/song-list/user-public-name/' + userName)
  .then((response) => {
    if (response.status == 200) {
      response.json()
      .then((data) => {
        // TODO
        console.log(data); // DEBUG
      });
    } else if (response.status == 404) {
      showSystemMessage('No such user');
    } else {
      showSystemMessage('Unable to fetch playlist data');
    }
  })
  .catch(() => {
    showSystemMessage('Unable to fetch playlist data');
  });
});

// ------------------------------------------------------------

// Configure event listeners: Login page

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
          updateTopBar(true);
          navigateToHomePage();
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

accountButton.addEventListener('click', () => {
  accountButton.disabled = true;
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
      clearPageInputs();
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
        accountButton.disabled = false;
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
      accountButton.disabled = false;
      accountLoadingOverlay.hidden = true;
    }
  })
  .catch(() => {
    showSystemMessage('Unable to update account');
    accountButton.disabled = false;
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
});

navigateToPlaylistPage();