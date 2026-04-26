package br.com.payments.spotify.application.service;

public interface RedisServiceImpl
{
    String getTokenRedis( String id, String type);
    void saveTokenRedis(String id,String value, String type);
}
