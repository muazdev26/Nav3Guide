package com.plcoding.nav3_guide

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform