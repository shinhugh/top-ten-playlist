const api = {
  origin: location.origin,

  login: async (credentials) => {
    try {
      const response = await fetch(api.origin + '/api/auth', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
      });
      return {
        status: response.status
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  logout: async () => {
    try {
      const response = await fetch(api.origin + '/api/auth', {
        method: 'DELETE'
      });
      return {
        status: response.status
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  createUser: async (user) => {
    try {
      const response = await fetch(api.origin + '/api/user', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
      });
      return {
        status: response.status
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  readUserById: async (id) => {
    try {
      const response = await fetch(api.origin + '/api/user/id/' + id);
      let data;
      try {
        data = await response.json();
      } catch {
        data = null;
      }
      return {
        status: response.status,
        data: data
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  readUserBySession: async () => {
    try {
      const response = await fetch(api.origin + '/api/user/session');
      let data;
      try {
        data = await response.json();
      } catch {
        data = null;
      }
      return {
        status: response.status,
        data: data
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  readUserByPublicName: async (publicName) => {
    try {
      const response = await fetch(api.origin + '/api/user/public-name/' + publicName);
      let data;
      try {
        data = await response.json();
      } catch {
        data = null;
      }
      return {
        status: response.status,
        data: data
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  updateUserById: async (id, user) => {
    try {
      const response = await fetch(api.origin + '/api/user/id/' + id, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
      });
      return {
        status: response.status
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  updateUserBySession: async (user) => {
    try {
      const response = await fetch(api.origin + '/api/user/session', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
      });
      return {
        status: response.status
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  deleteUserById: async (id) => {
    try {
      const response = await fetch(api.origin + '/api/user/id/' + id, {
        method: 'DELETE'
      });
      return {
        status: response.status
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  deleteUserBySession: async () => {
    try {
      const response = await fetch(api.origin + '/api/user/session', {
        method: 'DELETE'
      });
      return {
        status: response.status
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  createSongList: async (songList) => {
    try {
      const response = await fetch(api.origin + '/api/song-list', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(songList)
      });
      return {
        status: response.status
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  readSongListById: async (id) => {
    try {
      const response = await fetch(api.origin + '/api/song-list/id/' + id);
      let data;
      try {
        data = await response.json();
      } catch {
        data = null;
      }
      return {
        status: response.status,
        data: data
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  readSongListBySession: async () => {
    try {
      const response = await fetch(api.origin + '/api/song-list/session');
      let data;
      try {
        data = await response.json();
      } catch {
        data = null;
      }
      return {
        status: response.status,
        data: data
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  readSongListByUserPublicName: async (userPublicName) => {
    try {
      const response = await fetch(api.origin + '/api/song-list/user-public-name/' + userPublicName);
      let data;
      try {
        data = await response.json();
      } catch {
        data = null;
      }
      return {
        status: response.status,
        data: data
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  updateSongListById: async (id, songList) => {
    try {
      const response = await fetch(api.origin + '/api/song-list/id/' + id, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(songList)
      });
      return {
        status: response.status
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  updateSongListBySession: async (songList) => {
    try {
      const response = await fetch(api.origin + '/api/song-list/session', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(songList)
      });
      return {
        status: response.status
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  deleteSongListById: async (id) => {
    try {
      const response = await fetch(api.origin + '/api/song-list/id/' + id, {
        method: 'DELETE'
      });
      return {
        status: response.status
      };
    } catch {
      return {
        status: 0
      };
    }
  },

  deleteSongListBySession: async () => {
    try {
      const response = await fetch(api.origin + '/api/song-list/session', {
        method: 'DELETE'
      });
      return {
        status: response.status
      };
    } catch {
      return {
        status: 0
      };
    }
  }
};