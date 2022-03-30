import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import * as SectorActions from "./sector.action";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, map, switchMap} from "rxjs/operators";
import {environment} from "../../../../environments/environment";
import {of} from "rxjs";
import {SectorDataModel} from "../models/sectorData.model";
import {Sector} from "./sector.reducer";

@Injectable()
export class SectorEffects {

  fetchAllSectors$ = createEffect(
    () => this.actions$.pipe(
      ofType(SectorActions.startAllSectorFetch),
      switchMap(_ => {
        return this.http
          .get<Sector[]>(environment.backendURL + '/sector')
          .pipe(
            map(response => {
              return SectorActions.allSectorFetchSuccess({sectors: response})
            }),
            catchError(this.handleError)
          )
      })
    )
  )

  saveSectorData$ = createEffect(
    () => this.actions$.pipe(
      ofType(SectorActions.startSaving),
      switchMap(dataForSaving => {
          let url = environment.backendURL + '/user';
          if (dataForSaving.sectorData.userId) {
            url = url + '/' + dataForSaving.sectorData.userId;
          }
          return this.http
            .post<{ userId: string }>(url, dataForSaving.sectorData)
            .pipe(
              map(response => {
                return SectorActions.saveSuccess({id: response.userId})
              }),
              catchError(this.handleError)
            )
        }
      )
    )
  )

  fetchSectorData$ = createEffect(
    () => this.actions$.pipe(
      ofType(SectorActions.saveSuccess),
      switchMap(data => {
          return this.http
            .get<SectorDataModel>(environment.backendURL + '/user/' + data.id)
            .pipe(
              map(sectorData => {
                  return SectorActions.endFetch({sectorData})
                }
              ),
              catchError(this.handleError)
            )
        }
      )
    )
  )

  constructor(
    private actions$: Actions,
    private http: HttpClient
  ) {
  }

  handleError(error: HttpErrorResponse) {
    console.log(error);
    let errorMsg;
    if (error?.error?.message) {
      errorMsg = error?.error?.message;
    } else {
      errorMsg = error;
    }
    return of(SectorActions.error({error: errorMsg}))
  }
}
