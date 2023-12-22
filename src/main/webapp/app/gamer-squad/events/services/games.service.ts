import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { IGame } from '../../../entities/game/game.model';

@Injectable()
export class GamesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/v1/games');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<IGame> {
    return this.http.get<IGame>(`${this.resourceUrl}/${id}`);
  }
}
