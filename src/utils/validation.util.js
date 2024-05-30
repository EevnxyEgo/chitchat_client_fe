import * as yup from "yup"

export const signUpSchema=yup.object({
    name:yup.string()
    .required('name is required')
    .matches(/^[Aa-z-Z_ ]*$/, "No special characters allowed")
    .min(2,"name must be between 2 and 20 characters long")
    .max(20,"name must be between 2 and 20 characters long"),
    email:yup.string()
    .required('email address is required')
    .email("invalid email address"),
    status:yup.string()
    .max(64,"status must less than 64 characters"),
    password:yup.string()
    .required("password is required")
    .matches(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/,
"Password must contain atleast 6 characters, 1 uppercase, 1 lowercase, 1 number, amd 1 special character"),
});

export const signInSchema=yup.object({
    email:yup.string()
    .required('email address is required')
    .email("invalid email address"),
    password:yup.string()
    .required("password is required")
});