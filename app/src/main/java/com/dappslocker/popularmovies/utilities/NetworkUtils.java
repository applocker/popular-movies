/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dappslocker.popularmovies.utilities;
import android.util.Log;

/**
 * These utilities will be used to communicate with the movies servers.
 */
public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String POPULAR_MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String POPULAR_ENDPOINT = "popular";
    private static final String TOP_RATED_ENDPOINT = "top_rated";
    private static final String POPULAR_MOVIES_IMAGES_URL_BASE ="http://image.tmdb.org/t/p/w185/";
    private static final String POPULAR_MOVIES_IMAGES_URL_BASE_DETAIL ="http://image.tmdb.org/t/p/w342/";

    public static String getPopularMoviesImagesUrlBase() {
        return POPULAR_MOVIES_IMAGES_URL_BASE;
    }

    public static String getPopularMoviesImagesUrlBaseDetail() {
        return POPULAR_MOVIES_IMAGES_URL_BASE_DETAIL;
    }

    public static String getPopularMoviesBaseUrl() {
        return POPULAR_MOVIES_BASE_URL;
    }

    public static String getEndpoint(String pref){
        String endPoint;
        switch(pref){
            case "popular":
                endPoint = POPULAR_ENDPOINT;
                break;
            case "top rated":
                endPoint = TOP_RATED_ENDPOINT;
                break;
            default:
                endPoint = POPULAR_ENDPOINT;
        }
        return  endPoint;
    }

}