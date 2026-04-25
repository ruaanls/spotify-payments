package br.com.payments.spotify.application.service;

public interface RedisServiceImpl
{
    String getTokenRedis(String id, String type);
}
