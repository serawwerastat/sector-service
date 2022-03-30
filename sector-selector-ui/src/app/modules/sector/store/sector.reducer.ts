import {Action, createReducer, on} from "@ngrx/store";
import * as SectorActions from "./sector.action";
import {SectorDataModel} from "../models/sectorData.model";

export interface Sector {
  id: string,
  value: string,
  parent: boolean,
  level: number
}

export interface State {
  sectors: Sector[],
  userSector: SectorDataModel,
  error: string,
  isLoading: boolean
}

const initialState: State = {
  sectors: [],
  userSector: null,
  error: '',
  isLoading: false
}

const _sectorReducer = createReducer(
  initialState,

  on(
    SectorActions.startAllSectorFetch,
    (sectorState) => ({
      ...sectorState,
      isLoading: true
    })
  ),

  on(
    SectorActions.allSectorFetchSuccess,
    (sectorState, action) => ({
      ...sectorState,
      sectors: action.sectors,
      isLoading: false
    })
  ),

  on(
    SectorActions.startSaving,
    (sectorState) => ({
      ...sectorState,
      isLoading: true
    })
  ),

  on(
    SectorActions.endFetch,
    (sectorState, action) => ({
      ...sectorState,
      userSector: action.sectorData,
      isLoading: false
    })
  ),

  on(
    SectorActions.error,
    (sectorState, action) => ({
      ...sectorState,
      error: action.error,
      isLoading: false
    })
  ),

  on(
    SectorActions.clearError,
    (sectorState) => ({
      ...sectorState,
      error: null
    })
  )
)

export function sectorReducer(state: State, action: Action) {
  return _sectorReducer(state, action)
}
