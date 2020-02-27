package com.rewardtodo.presentation.mapper

import com.rewardtodo.R
import com.rewardtodo.domain.Reward

object RewardIconMapper: Mapper<Int, Reward.Icon> {
    override fun mapToView(type: Reward.Icon): Int {
        return when (type) {
            Reward.Icon.BATH -> R.drawable.ic_bath
            Reward.Icon.BEACH -> R.drawable.ic_beach
            Reward.Icon.BIKE -> R.drawable.ic_bike
            Reward.Icon.BOAT -> R.drawable.ic_boat
            Reward.Icon.BOOK -> R.drawable.ic_book
            Reward.Icon.BOOK_CLOSED -> R.drawable.ic_book_closed
            Reward.Icon.CAKE -> R.drawable.ic_cake
            Reward.Icon.CAMERA -> R.drawable.ic_camera
            Reward.Icon.CAR -> R.drawable.ic_car
            Reward.Icon.CHILD -> R.drawable.ic_child
            Reward.Icon.CLOCK -> R.drawable.ic_clock
            Reward.Icon.COCKTAIL -> R.drawable.ic_cocktail
            Reward.Icon.COFFEE -> R.drawable.ic_coffee
            Reward.Icon.COMPASS -> R.drawable.ic_compass
            Reward.Icon.COMPUTER -> R.drawable.ic_computer
            Reward.Icon.COUCH -> R.drawable.ic_couch
            Reward.Icon.DOODLE -> R.drawable.ic_doodle
            Reward.Icon.DRINK -> R.drawable.ic_drink
            Reward.Icon.EURO -> R.drawable.ic_euro
            Reward.Icon.FIRE -> R.drawable.ic_fire
            Reward.Icon.FLIGHT -> R.drawable.ic_flight
            Reward.Icon.FLOWER -> R.drawable.ic_flower
            Reward.Icon.FRIEND -> R.drawable.ic_friend
            Reward.Icon.GAME -> R.drawable.ic_game
            Reward.Icon.GOLF -> R.drawable.ic_golf
            Reward.Icon.HEADSET -> R.drawable.ic_headset
            Reward.Icon.LUGGAGE -> R.drawable.ic_luggage
            Reward.Icon.MEAL -> R.drawable.ic_meal
            Reward.Icon.MIC -> R.drawable.ic_mic
            Reward.Icon.MONEY -> R.drawable.ic_money
            Reward.Icon.MOTORCYCLE -> R.drawable.ic_motorcycle
            Reward.Icon.MOUNTAINS -> R.drawable.ic_mountains
            Reward.Icon.MOVIE -> R.drawable.ic_movie
            Reward.Icon.MUSIC -> R.drawable.ic_music
            Reward.Icon.MUSIC_NOTE -> R.drawable.ic_music_note
            Reward.Icon.PAINT -> R.drawable.ic_paint
            Reward.Icon.PAINTBRUSH -> R.drawable.ic_paintbrush
            Reward.Icon.PENCIL -> R.drawable.ic_pencil
            Reward.Icon.PERSON -> R.drawable.ic_person
            Reward.Icon.PHONE -> R.drawable.ic_phone
            Reward.Icon.PHONE_CALL -> R.drawable.ic_phone_call
            Reward.Icon.PIZZA -> R.drawable.ic_pizza
            Reward.Icon.PUZZLE -> R.drawable.ic_puzzle
            Reward.Icon.RUN -> R.drawable.ic_run
            Reward.Icon.SHOPPING -> R.drawable.ic_shopping
            Reward.Icon.SLEEP -> R.drawable.ic_sleep
            Reward.Icon.SNACK -> R.drawable.ic_snack
            Reward.Icon.SPA -> R.drawable.ic_spa
            Reward.Icon.SPEAKER -> R.drawable.ic_speaker
            Reward.Icon.STORE -> R.drawable.ic_store
            Reward.Icon.SWIM -> R.drawable.ic_swim
            Reward.Icon.TAGS -> R.drawable.ic_tags
            Reward.Icon.TICKET -> R.drawable.ic_ticket
            Reward.Icon.TRANSLATE -> R.drawable.ic_translate
            Reward.Icon.VIDEO_GAME -> R.drawable.ic_video_game
            Reward.Icon.WALK -> R.drawable.ic_walk
            Reward.Icon.WORKOUT -> R.drawable.ic_workout
        }
    }

    override fun mapFromView(type: Int): Reward.Icon {
        return when (type) {
            R.drawable.ic_bath -> Reward.Icon.BATH
            R.drawable.ic_beach -> Reward.Icon.BEACH
            R.drawable.ic_bike -> Reward.Icon.BIKE
            R.drawable.ic_boat -> Reward.Icon.BOAT
            R.drawable.ic_book -> Reward.Icon.BOOK
            R.drawable.ic_book_closed -> Reward.Icon.BOOK_CLOSED
            R.drawable.ic_cake -> Reward.Icon.CAKE
            R.drawable.ic_camera -> Reward.Icon.CAMERA
            R.drawable.ic_car -> Reward.Icon.CAR
            R.drawable.ic_child -> Reward.Icon.CHILD
            R.drawable.ic_clock -> Reward.Icon.CLOCK
            R.drawable.ic_cocktail -> Reward.Icon.COCKTAIL
            R.drawable.ic_coffee -> Reward.Icon.COFFEE
            R.drawable.ic_compass -> Reward.Icon.COMPASS
            R.drawable.ic_computer -> Reward.Icon.COMPUTER
            R.drawable.ic_couch -> Reward.Icon.COUCH
            R.drawable.ic_doodle -> Reward.Icon.DOODLE
            R.drawable.ic_drink -> Reward.Icon.DRINK
            R.drawable.ic_euro -> Reward.Icon.EURO
            R.drawable.ic_fire -> Reward.Icon.FIRE
            R.drawable.ic_flight -> Reward.Icon.FLIGHT
            R.drawable.ic_flower -> Reward.Icon.FLOWER
            R.drawable.ic_friend -> Reward.Icon.FRIEND
            R.drawable.ic_game -> Reward.Icon.GAME
            R.drawable.ic_golf -> Reward.Icon.GOLF
            R.drawable.ic_headset -> Reward.Icon.HEADSET
            R.drawable.ic_luggage -> Reward.Icon.LUGGAGE
            R.drawable.ic_meal -> Reward.Icon.MEAL
            R.drawable.ic_mic -> Reward.Icon.MIC
            R.drawable.ic_money -> Reward.Icon.MONEY
            R.drawable.ic_motorcycle -> Reward.Icon.MOTORCYCLE
            R.drawable.ic_mountains -> Reward.Icon.MOUNTAINS
            R.drawable.ic_movie -> Reward.Icon.MOVIE
            R.drawable.ic_music -> Reward.Icon.MUSIC
            R.drawable.ic_music_note -> Reward.Icon.MUSIC_NOTE
            R.drawable.ic_paint -> Reward.Icon.PAINT
            R.drawable.ic_paintbrush -> Reward.Icon.PAINTBRUSH
            R.drawable.ic_pencil -> Reward.Icon.PENCIL
            R.drawable.ic_person -> Reward.Icon.PERSON
            R.drawable.ic_phone -> Reward.Icon.PHONE
            R.drawable.ic_phone_call -> Reward.Icon.PHONE_CALL
            R.drawable.ic_pizza -> Reward.Icon.PIZZA
            R.drawable.ic_puzzle -> Reward.Icon.PUZZLE
            R.drawable.ic_run -> Reward.Icon.RUN
            R.drawable.ic_shopping -> Reward.Icon.SHOPPING
            R.drawable.ic_sleep -> Reward.Icon.SLEEP
            R.drawable.ic_snack -> Reward.Icon.SNACK
            R.drawable.ic_spa -> Reward.Icon.SPA
            R.drawable.ic_speaker -> Reward.Icon.SPEAKER
            R.drawable.ic_store -> Reward.Icon.STORE
            R.drawable.ic_swim -> Reward.Icon.SWIM
            R.drawable.ic_tags -> Reward.Icon.TAGS
            R.drawable.ic_ticket -> Reward.Icon.TICKET
            R.drawable.ic_translate -> Reward.Icon.TRANSLATE
            R.drawable.ic_video_game -> Reward.Icon.VIDEO_GAME
            R.drawable.ic_walk -> Reward.Icon.WALK
            R.drawable.ic_workout -> Reward.Icon.WORKOUT
            else -> throw IllegalArgumentException("Res id does not map to a Reward.Icon value")
        }
    }
}