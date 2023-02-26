# Top 10 Playlist

*This project is a work-in-progress.*

A web API and website for creating and sharing a playlist of your top 10 songs.

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
on the userAccount's personal accounts.

YouTube, Spotify, and SoundCloud are supported.

This service is hosted on an AWS EC2 instance. The link is here: *Coming soon!*

## Models

![Models](/readme_assets/models.png)

## Architecture

![Architecture](/readme_assets/architecture.png)

The Spring framework is used to simplify and build this system. Instead of
deploying a dedicated machine for each endpoint and installing an actual reverse
proxy, everything is done via code on a single server. A controller is written
for each endpoint, and Spring's built-in mechanisms handle the routing to the
correct controllers. This is more than enough for the expected amount of traffic
(essentially none) for this project. Needless to say, load balancers are not
necessary.

For the database, MySQL is used, but this can easily be changed. For
simplicity's sake, the two databases are merged into one. There is no functional
difference, as each model exists in each own table.

## Flows

Not all flows are covered; only those for application-specific functionality
are shown. Flows for more generic functionality (e.g. account creation, login,
etc.) are not shown here.

### Create new song list

![Flow: Create new song list](/readme_assets/flow_create_song_list.png)

### Modify existing song list

![Flow: Modify existing song list](/readme_assets/flow_modify_song_list.png)

### View existing song list

![Flow: View existing song list](/readme_assets/flow_view_song_list.png)
