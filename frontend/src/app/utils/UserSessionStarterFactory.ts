import { SessionService } from '../services/session.service';

export function userSessionStarterFactory(provider: SessionService) {
    return () => provider.startSessionOnApplicationBootstrap();
}