import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const CONVERSATION_ENDPOINT = `${process.env.REACT_APP_API_ENDPOINT}/conversation`;
const MESSAGE_ENDPOINT = `${process.env.REACT_APP_API_ENDPOINT}/message`;

const initialState = {
    status:"",
    error:"",
    conversations:[],
    activeConversation: {},
    messages:[],
    notifications: [],
};

export const getConversations = createAsyncThunk(
    "conervsation/all",
    async (token, { rejectWithValue }) => {
      try {
        const { data } = await axios.get(CONVERSATION_ENDPOINT, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        return data;
      } catch (error) {
        return rejectWithValue(error.response.data.error.message);
      }
    }
  );

export const openCreateConversation = createAsyncThunk(
    "conervsation/opencreate",
    async (values, { rejectWithValue }) => {
      const {token, receiverId } = values;
      try {
        const { data } = await axios.post(CONVERSATION_ENDPOINT, {receiverId}, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        return data;
      } catch (error) {
        return rejectWithValue(error.response.data.error.message);
      }
    }
  );

export const getConversationMessages = createAsyncThunk(
    "conervsation/messages",
    async (values, { rejectWithValue }) => {
      const {token, conversationId } = values;
      try {
        const { data } = await axios.get(`${MESSAGE_ENDPOINT}/${conversationId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        return data;
      } catch (error) {
        return rejectWithValue(error.response.data.error.message);
      }
    }
  );

export const sendMessage = createAsyncThunk(
    "message/send",
    async (values, { rejectWithValue }) => {
      const {token, message, conversationId, files } = values;
      try {
        const { data } = await axios.post(MESSAGE_ENDPOINT, {
          message,conversationId,files
        }, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        return data;
      } catch (error) {
        return rejectWithValue(error.response.data.error.message);
      }
    }
  );

export const chatSlice = createSlice({
  name: "chat",
  initialState,
  reducers: {
    setActiveConversation: (state, action) => {
      state.activeConversation = action.payload;
    },
  },
  extraReducers(builder) {
      builder
        .addCase(getConversations.pending, (state, action) => {
          state.status = "loading";
        })
        .addCase(getConversations.fulfilled, (state, action) => {
          state.status = "succeeded";
          state.conversations = action.payload;
        })
        .addCase(getConversations.rejected, (state, action) => {
          state.status = "failed";
          state.error = action.payload;
        })
        .addCase(openCreateConversation.pending, (state, action) => {
          state.status = "loading";
        })
        .addCase(openCreateConversation.fulfilled, (state, action) => {
          state.status = "succeeded";
          state.activeConversation = action.payload;
        })
        .addCase(openCreateConversation.rejected, (state, action) => {
          state.status = "failed";
          state.error = action.payload;
        })
        .addCase(getConversationMessages.pending, (state, action) => {
          state.status = "loading";
        })
        .addCase(getConversationMessages.fulfilled, (state, action) => {
          state.status = "succeeded";
          state.messages = action.payload;
        })
        .addCase(getConversationMessages.rejected, (state, action) => {
          state.status = "failed";
          state.error = action.payload;
        })
        .addCase(sendMessage.pending, (state, action) => {
          state.status = "loading";
        })
        .addCase(sendMessage.fulfilled, (state, action) => {
          state.status = "succeeded";
          state.messages = [...state.messages, action.payload];
          let conversation={...action.payload.conversation, latestMessage:action.payload,};
          let newConversations=[...state.conversations].filter((c)=>c._id !== conversation._id);
          newConversations.unshift(conversation);
          state.conversations=newConversations;
        })
        .addCase(sendMessage.rejected, (state, action) => {
          state.status = "failed";
          state.error = action.payload;
        });
  },
});



export const {setActiveConversation} = chatSlice.actions;

export default chatSlice.reducer;