package com.spotify.architecture.client;

/**
 * Клас-сервіс для взаємодії з Spotify Web API.
 * Реалізовано в межах аналізу мікросервісної архітектури під запит користувача.
 */
public class SpotifyService {

    private static final String BASE_URL = "https://api.spotify.com/v1";

    /**
     * Пошук треку за назвою.
     * Реалізує логіку взаємодії з мікросервісом пошуку.
     * Запит: GET https://api.spotify.com/v1/search?q=track:Name&type=track
     */
    public void searchTrack(String trackName) {
        String url = BASE_URL + "/search?q=track:" + trackName + "&type=track";
        executeRequest(url, "Пошук треку");
    }

    /**
     * Отримання повної інформації про трек.
     * Запит: GET https://api.spotify.com/v1/tracks/{track_id}
     */
    public void getTrackInfo(String trackId) {
        String url = BASE_URL + "/tracks/" + trackId;
        executeRequest(url, "Інформація про трек");
    }

    /**
     * Отримання даних про альбом.
     * Запит: GET https://api.spotify.com/v1/albums/{album_id}
     */
    public void getAlbumInfo(String albumId) {
        String url = BASE_URL + "/albums/" + albumId;
        executeRequest(url, "Інформація про альбом");
    }

    /**
     * Отримання профілю виконавця.
     * Запит: GET https://api.spotify.com/v1/artists/{artist_id}
     */
    public void getArtistData(String artistId) {
        String url = BASE_URL + "/artists/" + artistId;
        executeRequest(url, "Дані про виконавця");
    }

    /**
     * Вибірка треків із конкретного плейлиста.
     * Запит: GET https://api.spotify.com/v1/playlists/{playlist_id}/tracks
     */
    public void getPlaylistTracks(String playlistId) {
        String url = BASE_URL + "/playlists/" + playlistId + "/tracks";
        executeRequest(url, "Треки у плейлисті");
    }

    /**
     * Внутрішній метод для імітації виконання HTTP-запиту.
     */
    private void executeRequest(String url, String operationName) {
        System.out.println("[API Gateway] Виконується операція: " + operationName);
        System.out.println("[HTTP GET] URL: " + url);
        System.out.println("[Response] Статус: 200 OK");
    }
}
