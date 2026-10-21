import handler from "./handler";

export const getUserDetails = async () => {
    const response = await handler.get(
        "/userdetails",
    );

    return response.data;
};