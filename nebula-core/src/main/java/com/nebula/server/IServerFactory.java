package com.nebula.server;

import java.util.Map;

public interface IServerFactory {

    IServer createServer(Map<String, String> config);
}