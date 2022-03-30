import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Sector} from "../store/sector.reducer";
import {Store} from "@ngrx/store";
import * as fromApp from "../../../store/app.reducer";
import {Actions, ofType} from "@ngrx/effects";
import {Observable, of} from "rxjs";
import * as SectorActions from "../store/sector.action";
import {map, switchMap, take} from "rxjs/operators";
import {Injectable} from "@angular/core";

@Injectable({providedIn: "root"})
export class SectorResolverService implements Resolve<{ sectors: Sector[] }> {

  constructor(
    private store: Store<fromApp.AppState>,
    private actions$: Actions
  ) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<{ sectors: Sector[] }> | Promise<{ sectors: Sector[] }> | { sectors: Sector[] } {
    return this.store.select("sector").pipe(
      take(1),
      map(sectorState => sectorState.sectors),
      switchMap(sectors => {
        if (sectors.length === 0) {
          this.store.dispatch(SectorActions.startAllSectorFetch())
          return this.actions$.pipe(
            ofType(SectorActions.allSectorFetchSuccess),
            take(1)
          )
        } else {
          return of({sectors});
        }
      })
    )
  }
}
