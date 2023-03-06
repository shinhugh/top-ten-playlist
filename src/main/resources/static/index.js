const topBarHomeLink = document.getElementById('top-bar-home-link');
const topBarSearchInput = document.getElementById('top-bar-search-input');
const topBarSearchButton = document.getElementById('top-bar-search-button');
const topBarLoginLink = document.getElementById('top-bar-login-link');
const topBarSignUpLink = document.getElementById('top-bar-sign-up-link');

const homePageRoot = document.getElementById('home-page-root');

const loginPageRoot = document.getElementById('login-page-root');
const loginNameInput = document.getElementById('login-name-input');
const loginPasswordInput = document.getElementById('login-password-input');
const loginButton = document.getElementById('login-button');

const signUpPageRoot = document.getElementById('sign-up-page-root');

const playlistPageRoot = document.getElementById('playlist-page-root');

const navigateToHomePage = () => {
  loginPageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  homePageRoot.hidden = false;
};

const navigateToLoginPage = () => {
  homePageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  loginPageRoot.hidden = false;
};

const navigateToSignUpPage = () => {
  homePageRoot.hidden = true;
  loginPageRoot.hidden = true;
  playlistPageRoot.hidden = true;
  signUpPageRoot.hidden = false;
};

const navigateToPlaylistPage = () => {
  homePageRoot.hidden = true;
  loginPageRoot.hidden = true;
  signUpPageRoot.hidden = true;
  playlistPageRoot.hidden = false;
};

topBarHomeLink.addEventListener('click', () => {
  navigateToHomePage();
});

topBarLoginLink.addEventListener('click', () => {
  navigateToLoginPage();
});

topBarSignUpLink.addEventListener('click', () => {
  navigateToSignUpPage();
});

topBarSearchButton.addEventListener('click', () => {
  const userName = topBarSearchInput.value;
  fetch('http://localhost:8080/api/song-list/user-public-name/' + userName)
  .then((response) => response.json())
  .then((data) => {
    console.log(data);
  });
});

loginButton.addEventListener('click', () => {
  const loginName = loginNameInput.value;
  const loginPassword = loginPasswordInput.value;
  fetch('http://localhost:8080/api/auth', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      name: loginName,
      password: loginPassword
    })
  });
});

navigateToHomePage();