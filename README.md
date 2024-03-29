# Top 10 Playlist

A REST API and website for creating and sharing a playlist of your top 10 songs.

This service is currently down. Cloud computing isn't cheap and I am currently
working on other projects.

![Home page](/readme_assets/screenshot_home.png)
![Editor page](/readme_assets/screenshot_editor.png)
![Login page](/readme_assets/screenshot_login.png)

Sharing music is an excellent way to get to know others more deeply and to
diversify one's own palette. An extremely difficult yet fun task is to narrow
down one's lifetime of music taste to just 10 songs. This project was launched
to provide a straightforward tool for exactly this.

Why not just create a plain old playlist on an existing platform?

- There is a growing plethora of streaming platforms, and no single one contains
all music. This tool supports multiple platforms, enabling the creation of a
playlist that pulls from different sources.
- Some people would prefer not to pollute their own personal accounts with
playlists created just for the sake of sharing.
- This playlist can be shared as a simple link without exposing any information
on the user's personal accounts.

YouTube and SoundCloud are currently supported. Paste any link from these
platforms into the playlist editor!

## Implementation details

### Models

![Models](/readme_assets/models.png)

### Architecture

![Architecture](/readme_assets/architecture.png)

The Spring framework is used to simplify and build this system. Instead of
deploying a dedicated machine for each node and installing an actual reverse
proxy, everything is done in a monolithic fashion. This is perfectly fine for
the expected scale of this project. Should there ever be a need to shift to
proper microservices, the changes would be relatively straightforward; the
services can be broken out into separate code bases and deployed individually,
with the addition of network communication to and from the services.

MySQL is used as the database, but this can easily be changed. For simplicity's
sake, the two databases are merged into one. There is no functional difference,
as each model exists in each own table.

### Flows

Not all flows are covered; only those for application-specific functionality
are shown. Flows for more generic functionality (e.g. account creation, login,
etc.) are not shown here.

#### Create new song list

![Flow: Create new song list](/readme_assets/flow_create_song_list.png)

#### Modify existing song list

![Flow: Modify existing song list](/readme_assets/flow_modify_song_list.png)

#### View existing song list

![Flow: View existing song list](/readme_assets/flow_view_song_list.png)
