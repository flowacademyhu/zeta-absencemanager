import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { AbstractApiConnector } from "../models/connectors/AbstractApiConnector";
import { HttpClient } from "@angular/common/http";
import { AuthApiConnector } from "../models/connectors/AuthApiConnector";
import { UserApiConnector } from "../models/connectors/UserApiConnector";
import { AbsenceApiConnector } from "../models/connectors/AbsenceApiConnector";

export enum Connector {
  AUTH = "Auth",
  USER = "User",
  ABSENCE = "Absence"
}

@Injectable()
export class ApiCommunicationService {
  private apiBaseUrl: string = environment.baseUrl;
  private connectors: Map<Connector, AbstractApiConnector>;

  constructor(private http: HttpClient) {
    this.connectors = new Map<Connector, AbstractApiConnector>();

    this.registerConnector(
      Connector.AUTH,
      new AuthApiConnector(http, this.apiBaseUrl)
    );
    this.registerConnector(
      Connector.USER,
      new UserApiConnector(http, this.apiBaseUrl)
    );
    this.registerConnector(
      Connector.ABSENCE,
      new AbsenceApiConnector(http, this.apiBaseUrl)
    );
  }

  private registerConnector(id: Connector, connector: AbstractApiConnector) {
    if (this.connectors.has(id)) {
      console.error("Connector already registered.");
    }
    try {
      this.connectors.set(id, connector);
    } catch {
      console.error("Cannot set connector.");
    }
  }

  private getConnector(id: Connector): AbstractApiConnector {
    if (this.connectors.has(id)) {
      return this.connectors.get(id);
    } else {
      console.error("No connector is registered for this id.");
    }
  }

  public auth(): AuthApiConnector {
    return this.getConnector(Connector.AUTH) as AuthApiConnector;
  }

  public user(): UserApiConnector {
    return this.getConnector(Connector.USER) as UserApiConnector;
  }

  public absence(): AbsenceApiConnector {
    return this.getConnector(Connector.ABSENCE) as AbsenceApiConnector;
  }
}
