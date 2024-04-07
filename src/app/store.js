import { combineReducers, configureStore} from "@reduxjs/toolkit"
import userSlice from "../features/userSlice";
import storage from "redux-persist/lib/storage";
import persistReducer from "redux-persist/es/persistReducer";
import persistStore from "redux-persist/es/persistStore";
import createFilter from "redux-persist-transform-filter";

const saveUserOnlyFilter=createFilter("user", ["user"]);

const persistConfig = {
    key: "user",
    storage,
    whitelist: ["user"],
    transforms:[saveUserOnlyFilter],
};

const rootReducer = combineReducers({
    user: userSlice
});

const persistedReducer=persistReducer(persistConfig, rootReducer)

export const store=configureStore({
    reducer: persistedReducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware({
            serializableCheck: false,
        }),
    devTools: true,
});

export const persistor = persistStore(store);