import {ActionReducerMap} from "@ngrx/store";
import * as fromSector from "../modules/sector/store/sector.reducer";

export interface AppState {
  sector: fromSector.State;
}

export const appReducer: ActionReducerMap<AppState> = {
  sector: fromSector.sectorReducer
}
