export interface User {
  username:string,
  name: string,
  lastName: string,
  password: string,
  repeatPassword: string,
  role: string
}

export interface UserLoginRequest {
  username:string,
  password: string,
  token:string
}

export interface UserAndTokenRequest {
  user: User | null,
  token: string | null
}

export interface TokenUserAuthenticationRequest {
  isAuthenticated: boolean,
  token: string | null
}

export interface UsernameRequest {
  username: string | undefined
}

export interface TokenRequest {
  token: string | null
}

export interface UserState {
  isAuthenticated: boolean,
  token: string | null,
  currentUser: User | null,
  authentication: TokenUserAuthenticationRequest | null
}

export interface EditUserRequest {
  username: string | undefined,
  name: string | undefined,
  lastName: string | undefined,
  password: string | undefined,
  repeatPassword: string | undefined
}

export interface ProductUserRequest {
  productId: number | null,
  username: string | undefined,
}
