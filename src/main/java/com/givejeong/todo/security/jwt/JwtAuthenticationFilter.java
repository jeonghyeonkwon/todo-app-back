package com.givejeong.todo.security.jwt;


// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가있음
// /login 요청해서 username, password 정송하면 (post)
// UsernamePasswordAuthenticationFilter 동작을 함.
//@RequiredArgsConstructor
public class JwtAuthenticationFilter {//extends UsernamePasswordAuthenticationFilter {
//    private final AuthenticationManager authenticationManager;
//    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        System.out.println("JwtAuthenticationFilter : 로그인 시도중");
//        // 1. username, password 받아서
//        LoginDto login = null;
//        try{
////            BufferedReader br = request.getReader();
////            String input = null;
////            while((input=br.readLine())!=null){
////                System.out.println(input);
////            }
//            ObjectMapper om = new ObjectMapper();
//            System.out.println(request.getInputStream());
//            login = om.readValue(request.getInputStream(), LoginDto.class);
//            System.out.println(login);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword());
//            //PrincipalDetailsService의 loadUserByUsername() 함수가 실행 됨
//            Authentication authentication = authenticationManager.authenticate(authenticationToken);
//
//            //authentication 객체가 session영역에 저장됨. => 로그인이 되었다는 뜻
//            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//            System.out.println("로그인 완료됨 : "+ principalDetails.getAccount().getAccountId());
//
//            // authentication 객체가 session영역에 저장을 해야하고 그 방법이 return 해주면 됨.
//            // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고 하는 거임
//            // 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 근데 단지 권한 처리때문에 session 넣어줍니다.
//            return authentication;
//
//
//        // 2. 정상인지 로그인 시도를 해보는 거에요. authenticationManager로 로그인 시도를 하면!!
//        // PrinciaplDetailsService 가 호출 loadUserByUsername() 함수 실행됨
//        //
//        // 3.PrinciapalDetails를 세션에 담고 (권한 관리를 위해서)
//        // 4. JWT토큰을 만들어서 응답해주면 됨.
//
//    }
//    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행되요
//    //JWT 토큰을 만들어서 request요청한 사용자에게 JWT토큰을 response해주면 됨.
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        System.out.println("successfulAuthentication 실행됨 : 인증이 완료되었다는 뜻임");
//        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
//
//        // RSA방식은 아니고 Hash 암호 방식식
//       String jwtToken = JWT.create()
//                .withSubject("cos토큰")
//                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
//                .withClaim("id",principalDetails.getAccount().getId())
//                .withClaim("username",principalDetails.getAccount().getAccountId())
//                .sign(Algorithm.HMAC512("cos"));
//        System.out.println("jwt : " + jwtToken);
//        System.out.println("successfulAuthentication");
//       response.addHeader("Authorization","Bearer "+jwtToken);
//
//
//        System.out.println("success : " + principalDetails.getAccount().getAccountId());
//
//        //super.successfulAuthentication(request, response, chain, authResult);\
//
//    }
}
